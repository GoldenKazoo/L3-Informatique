/*
 * Ex9 — Large Letter Collider généralisé à n processus (anneau).
 *
 * Usage :
 *     ./ex9_llc_ring <n>
 *
 * Description :
 *   - Crée n processus formant un anneau via n pipes.
 *   - Le processus 0 envoie 'a'.
 *   - Chaque processus lit une lettre :
 *        -> affiche sa transformation (a -> A)
 *        -> attend un peu (sleep) pour rendre visible la propagation
 *        -> incrémente la lettre ('a'->'b', etc.) et l’envoie au suivant
 *   - Quand on dépasse 'z', on envoie '!' pour signaler la fin.
 */

#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>
#include <sys/wait.h>

static void die(const char *msg) {
    perror(msg);
    exit(EXIT_FAILURE);
}

int main(int argc, char **argv) {
    if (argc != 2) {
        fprintf(stderr, "Usage: %s <n_processus>\n", argv[0]);
        exit(EXIT_FAILURE);
    }

    int n = atoi(argv[1]);
    if (n < 2) {
        fprintf(stderr, "Il faut au moins 2 processus.\n");
        exit(EXIT_FAILURE);
    }

    int p[n][2];
    for (int i = 0; i < n; i++)
        if (pipe(p[i]) == -1)
            die("pipe");

    for (int i = 0; i < n; i++) {
        pid_t pid = fork();
        if (pid < 0) die("fork");

        if (pid == 0) {
            // Processus i : lit sur p[i][0], écrit sur p[(i+1)%n][1]
            for (int k = 0; k < n; k++) {
                if (k != i) close(p[k][0]);
                if (k != (i + 1) % n) close(p[k][1]);
            }

            int in  = p[i][0];
            int out = p[(i + 1) % n][1];
            char c;

            for (;;) {
                ssize_t r = read(in, &c, 1);
                if (r <= 0) break;   // fin du flux
                if (c == '!') {      // signal de fin
                    write(out, &c, 1);
                    break;
                }

                // Transformation locale
                char upper = (c >= 'a' && c <= 'z') ? (c - 'a' + 'A') : c;

                // Affichage clair de la transformation
                dprintf(STDOUT_FILENO,
                        "[P%d pid=%d] '%c' → '%c' → envoie '%c'\n",
                        i, getpid(), c, upper, (c == 'z') ? '!' : c + 1);

                // Pause pour visualiser la propagation
                usleep(200000); // 0.2 s

                // Envoi au suivant
                if (c == 'z') {
                    char stop = '!';
                    write(out, &stop, 1);
                    break;
                }

                char next = c + 1;
                write(out, &next, 1);
            }

            close(in);
            close(out);
            _exit(0);
        }
    }

    // Père : ferme les lectures, amorce avec 'a'
    for (int i = 0; i < n; i++)
        close(p[i][0]);

    char start = 'a';
    write(p[0][1], &start, 1);

    // Fermer les écritures du père
    for (int i = 0; i < n; i++)
        close(p[i][1]);

    // Attendre tous les fils
    for (int i = 0; i < n; i++)
        wait(NULL);

    return 0;
}
