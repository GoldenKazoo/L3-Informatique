/*
 * Ex3 (variante) — Lecture non déterministe sur un seul pipe partagé.
 *
 * Trois fils lisent simultanément le même flux, par blocs de 16 octets.
 * Chaque octet est attribué à un seul lecteur, de manière non déterministe
 * (répartition faite par le noyau). Chaque fils renvoie ce qu’il lit au père,
 * qui affiche ensuite les résultats dans l’ordre (fils 1, 2, 3).
 */
#include <unistd.h>
#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <sys/wait.h>
#include <errno.h>

#define BUFSZ 16

static void die(const char *msg){ perror(msg); exit(EXIT_FAILURE); }

int main(void){
    int fd[2]; // pipe principal partagé entre les lecteurs
    if (pipe(fd) == -1) die("pipe main");

    const char *msg =
        "Lorem ipsum dolor sit amet, consectetur adipiscing elit. "
        "Phasellus pulvinar purus vel neque accumsan, non vehicula metus finibus. "
        "Lecture non deterministe avec 3 fils et buffer de 16 octets.\n";

    // Écrire tout le flux et fermer l'écriture
    if (write(fd[1], msg, strlen(msg)) < 0) die("write");
    close(fd[1]);

    // Pipes de retour (un par fils)
    int ret[3][2];
    for (int i = 0; i < 3; i++)
        if (pipe(ret[i]) == -1) die("pipe ret");

    for (int i = 0; i < 3; i++) {
        pid_t pid = fork();
        if (pid < 0) die("fork");
        if (pid == 0) {
            close(fd[1]); // inutile ici
            // Fermer les pipes de retour sauf le sien (en écriture)
            for (int j = 0; j < 3; j++) {
                close(ret[j][0]);
                if (j != i) close(ret[j][1]);
            }

            char buf[BUFSZ];
            ssize_t r;
            while ((r = read(fd[0], buf, BUFSZ)) > 0) {
                dprintf(ret[i][1], "[fils %d pid=%d] ", i+1, getpid());
                if (write(ret[i][1], buf, (size_t)r) < 0) die("write ret");
                dprintf(ret[i][1], "\n");
                sleep(1); // pour un entrelacement visible
            }

            close(fd[0]);
            close(ret[i][1]);
            _exit(0);
        }
    }

    // Père : ferme sa lecture du pipe principal et les écritures des pipes de retour
    close(fd[0]);
    for (int i = 0; i < 3; i++) close(ret[i][1]);

    // Lire et afficher les retours dans l’ordre des fils
    char buf[256];
    for (int i = 0; i < 3; i++) {
        dprintf(STDOUT_FILENO, "\n=== Résultats du fils %d ===\n", i+1);
        ssize_t r;
        while ((r = read(ret[i][0], buf, sizeof buf)) > 0)
            write(STDOUT_FILENO, buf, (size_t)r);
        close(ret[i][0]);
    }

    for (int i = 0; i < 3; i++) {
        int st; wait(&st);
    }

    return 0;
}
