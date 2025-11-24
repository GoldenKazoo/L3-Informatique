
/*
 * Ex8 — Large Letter Collider (2 processus, 2 pipes).
 * Père amorce avec 'a' vers le fils. Chacun :
 *  - lit un octet, affiche la transformation locale,
 *  - incrémente la lettre et la renvoie à l'autre,
 *  - stoppe à 'z' (la prochaine valeur sert de marqueur d'arrêt).
 *
 * Points clefs :
 *  - fermeture précise des bouts de pipes pour éviter des blocages.
 *  - protocole d'arrêt (ici: on transmet un octet > 'z' pour terminer proprement).
 */
#include <unistd.h>
#include <stdio.h>
#include <stdlib.h>
#include <sys/wait.h>

static void die(const char *msg){ perror(msg); exit(EXIT_FAILURE); }

int main(void){
    int p2f[2], f2p[2];
    if(pipe(p2f) == -1) die("pipe p2f");
    if(pipe(f2p) == -1) die("pipe f2p");

    pid_t pid = fork();
    if(pid < 0) die("fork");

    if(pid == 0){
        // Fils: lit p2f[0], écrit f2p[1]
        close(p2f[1]); close(f2p[0]);
        for(;;){
            unsigned char c;
            ssize_t r = read(p2f[0], &c, 1);
            if(r <= 0) break;
            if(c > 'z'){ // marqueur stop
                write(f2p[1], &c, 1);
                break;
            }
            dprintf(STDOUT_FILENO, "Fils: %c -> %c\n", c, (c>='a'&&c<='z') ? (char)(c-'a'+'A') : c);
            c = (unsigned char)(c + 1);
            write(f2p[1], &c, 1);
            usleep(100000); // pour mieux voir l'alternance
        }
        close(p2f[0]); close(f2p[1]);
        _exit(0);
    } else {
        usleep(10000); // pour mieux voir l'alternance
        // Père: écrit p2f[1], lit f2p[0]
        close(p2f[0]); close(f2p[1]);
        unsigned char c = 'a';
        write(p2f[1], &c, 1);
        for(;;){
            ssize_t r = read(f2p[0], &c, 1);
            if(r <= 0) break;
            if(c > 'z'){ // stop
                break;
            }
            dprintf(STDOUT_FILENO, "Père: %c -> %c\n", c, (c>='a'&&c<='z') ? (char)(c-'a'+'A') : c);
            if(c == 'z'){
                unsigned char stop = (unsigned char)('z' + 1);
                write(p2f[1], &stop, 1);
                break;
            }
            c = (unsigned char)(c + 1);
            write(p2f[1], &c, 1);
        }
        close(p2f[1]); close(f2p[0]);
        int st; wait(&st);
        return 0;
    }
}
