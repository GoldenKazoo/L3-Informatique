
/*
 * Ex1 — Tube anonyme : premier échange.
 * Le père écrit "abc" dans le tube, le fils lit 3 octets et les affiche.
 *
 * Points d'attention :
 *  - Toujours vérifier les retours d'erreur.
 *  - Fermer les descripteurs inutiles dans chaque branche après fork().
 *  - read() peut lire moins que demandé : ici on sait que 3 octets arrivent d'un coup,
 *    mais on illustre une petite boucle de lecture robuste.
 */
#include <unistd.h>
#include <stdio.h>
#include <stdlib.h>
#include <sys/wait.h>
#include <errno.h>
#include <string.h>

static void die(const char *msg){
    perror(msg);
    exit(EXIT_FAILURE);
}

static ssize_t read_full(int fd, void *buf, size_t n){
    size_t off = 0;
    while(off < n){
        ssize_t r = read(fd, (char*)buf + off, n - off);
        if(r < 0){
            if(errno == EINTR) continue;
            return -1;
        }
        if(r == 0) break; // EOF
        off += (size_t)r;
    }
    return (ssize_t)off;
}

int main(void){
    int fd[2];
    if(pipe(fd) == -1) die("pipe");

    pid_t pid = fork();
    if(pid < 0) die("fork");

    if(pid == 0){
        // Fils : lit 3 octets
        close(fd[1]); // inutile en lecture
        char buf[3];
        if(read_full(fd[0], buf, 3) != 3) die("read_full");
        close(fd[0]);

        // Affichage
        if(write(STDOUT_FILENO, buf, 3) != 3) die("write");
        write(STDOUT_FILENO, "\n", 1);
        _exit(0);
    } else {
        // Père : écrit "abc"
        close(fd[0]); // inutile en écriture
        const char msg[3] = {'a','b','c'};
        if(write(fd[1], msg, 3) != 3) die("write");
        close(fd[1]);

        // Attendre le fils
        int st;
        wait(&st);
        return WIFEXITED(st) ? 0 : 1;
    }
}
