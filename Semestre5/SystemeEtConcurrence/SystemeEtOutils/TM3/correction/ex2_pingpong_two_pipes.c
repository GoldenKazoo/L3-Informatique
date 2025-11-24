
/*
 * Ex2 — Communication bidirectionnelle avec deux tubes.
 * On crée p2f (père->fils) et f2p (fils->père).
 * Protocole : père envoie "ping\n", fils répond "pong\n".
 *
 * Points d'attention :
 *  - Chaque processus ferme les extrémités inutiles de chaque pipe.
 *  - Les tailles d'écriture modestes tiennent en un seul write().
 */
#include <unistd.h>
#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <sys/wait.h>
#include <errno.h>

static void die(const char *msg){ perror(msg); exit(EXIT_FAILURE); }

static ssize_t read_line(int fd, char *buf, size_t cap){
    size_t i = 0;
    while(i + 1 < cap){
        char c;
        ssize_t r = read(fd, &c, 1);
        if(r < 0){
            if(errno == EINTR) continue;
            return -1;
        }
        if(r == 0) break;
        buf[i++] = c;
        if(c == '\n') break;
    }
    buf[i] = '\0';
    return (ssize_t)i;
}

int main(void){
    int p2f[2], f2p[2];
    if(pipe(p2f) == -1) die("pipe p2f");
    if(pipe(f2p) == -1) die("pipe f2p");

    pid_t pid = fork();
    if(pid < 0) die("fork");

    if(pid == 0){
        // Fils
        close(p2f[1]); // lit depuis p2f[0]
        close(f2p[0]); // écrit dans f2p[1]

        char buf[16];
        if(read_line(p2f[0], buf, sizeof(buf)) <= 0) die("read_line");
        dprintf(STDOUT_FILENO, "Fils a reçu: %s", buf);

        const char *resp = "pong\n";
        if(write(f2p[1], resp, strlen(resp)) < 0) die("write");

        close(p2f[0]);
        close(f2p[1]);
        _exit(0);
    } else {
        // Père
        close(p2f[0]); // écrit vers p2f[1]
        close(f2p[1]); // lit depuis f2p[0]

        const char *msg = "ping\n";
        if(write(p2f[1], msg, strlen(msg)) < 0) die("write");

        char buf[16];
        if(read_line(f2p[0], buf, sizeof(buf)) <= 0) die("read_line");
        dprintf(STDOUT_FILENO, "Père a reçu: %s", buf);

        close(p2f[1]);
        close(f2p[0]);
        int st; wait(&st);
        return WIFEXITED(st) ? 0 : 1;
    }
}
