
/*
 * Ex4 — Reproduire: ls | grep .c | wc -l
 *
 * Schéma:
 *   [ls] --p1--> [grep .c] --p2--> [wc -l]
 *
 * On crée 2 pipes p1, p2. Chaque processus dup2() ses entrées/sorties
 * sur les bons descripteurs, puis execlp().
 */
#include <unistd.h>
#include <stdio.h>
#include <stdlib.h>
#include <sys/wait.h>

static void die(const char *msg){ perror(msg); exit(EXIT_FAILURE); }

int main(void){
    int p1[2], p2[2];
    if(pipe(p1) == -1) die("pipe p1");
    if(pipe(p2) == -1) die("pipe p2");

    pid_t c1 = fork();
    if(c1 < 0) die("fork c1");
    if(c1 == 0){
        // Fils1: ls -> écrit dans p1[1]
        if(dup2(p1[1], STDOUT_FILENO) == -1) die("dup2 ls->p1");
        close(p1[0]); close(p1[1]);
        close(p2[0]); close(p2[1]);
        execlp("ls", "ls", (char*)NULL);
        die("execlp ls");
    }

    pid_t c2 = fork();
    if(c2 < 0) die("fork c2");
    if(c2 == 0){
        // Fils2: grep .c  lit p1[0], écrit p2[1]
        if(dup2(p1[0], STDIN_FILENO) == -1) die("dup2 grep<-p1");
        if(dup2(p2[1], STDOUT_FILENO) == -1) die("dup2 grep->p2");
        close(p1[0]); close(p1[1]);
        close(p2[0]); close(p2[1]);
        execlp("grep", "grep", ".c", (char*)NULL);
        die("execlp grep");
    }

    pid_t c3 = fork();
    if(c3 < 0) die("fork c3");
    if(c3 == 0){
        // Fils3: wc -l  lit p2[0]
        if(dup2(p2[0], STDIN_FILENO) == -1) die("dup2 wc<-p2");
        close(p1[0]); close(p1[1]);
        close(p2[0]); close(p2[1]);
        execlp("wc", "wc", "-l", (char*)NULL);
        die("execlp wc");
    }

    // Père : ferme tout et attend
    close(p1[0]); close(p1[1]);
    close(p2[0]); close(p2[1]);
    int st;
    wait(&st); wait(&st); wait(&st);
    return 0;
}
