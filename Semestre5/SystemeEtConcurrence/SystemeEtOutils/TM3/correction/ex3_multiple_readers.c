/*
 * Ex3 — Un SEUL pipe, buffers de 16, découpage déterministe en 3 tiers.
 *
 * Idée clé :
 *  - Le père écrit TOUT le message dans le pipe, puis ferme l'écriture.
 *  - Il fork le fils 1 qui lit EXACTEMENT le 1er tiers (avec buffer=16).
 *    La lecture avance le "curseur" du pipe (partagé via le même file description).
 *  - Une fois le fils 1 terminé, le père fork le fils 2, qui lit le 2e tiers, etc.
 *  - Le fils 3 lit le reste (dernier tiers).
 * Points d'attention :
 *  - Chaque fils affiche un label avant et après sa lecture, pour clarifier l'affichage.
 * - Chaque fils lit en boucle, par morceaux de 16 octets, jusqu'à avoir lu EXACTEMENT
 * le nombre d'octets qui lui est assigné.
 * - Le père ferme l'écriture du pipe après avoir tout écrit, pour garantir un EOF
 *  aux lecteurs une fois tout lu.
 */
#include <unistd.h>
#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <sys/wait.h>
#include <errno.h>

#define BUFSZ 16

static void die(const char *msg){ perror(msg); exit(EXIT_FAILURE); }

/* Lit exactement n octets depuis fd, en morceaux de BUFSZ, et les écrit sur stdout
 * avec un préfixe (label) pour identifier le lecteur. */
static void read_exact_with_label(int fd, size_t n, const char *label){
    char buf[BUFSZ];
    size_t rem = n;
    dprintf(STDOUT_FILENO, "[%s] debut (n=%zu)\n", label, n);
    while(rem > 0){
        size_t want = rem < BUFSZ ? rem : BUFSZ;
        ssize_t r = read(fd, buf, want);
        if(r < 0){
            if(errno == EINTR) continue;
            die("read");
        }
        if(r == 0){
            // EOF prématuré: message plus court que prévu
            break;
        }
        if(write(STDOUT_FILENO, buf, (size_t)r) < 0) die("write");
        rem -= (size_t)r;
    }
    dprintf(STDOUT_FILENO, "\n[%s] fin (restait=%zu)\n", label, rem);
}

int main(void){
    int fd[2];
    if(pipe(fd) == -1) die("pipe");

    /* Message source */
    const char *msg =
        "Coucou les lecteurs!\nLorem ipsum dolor sit amet, consectetur adipiscing elit. "
        "Vivamus demo pipe unique: on veut un partage DETERMINE en trois tiers, "
        "avec des lectures en buffers de 16 octets.\n";
    size_t len = strlen(msg);

    /* Écriture complète puis fermeture écriture (pour garantir EOF quand tout sera lu) */
    if(write(fd[1], msg, len) < 0) die("write");
    if(close(fd[1]) == -1) die("close write");

    /* Calcul des tiers */
    size_t t1 = len / 3;
    size_t t2 = len / 3;
    size_t t3 = len - t1 - t2;

    /* Fils 1 : lit le 1er tiers */
    pid_t p = fork();
    if(p < 0) die("fork");
    if(p == 0){
        read_exact_with_label(fd[0], t1, "fils1 (tiers 1)");
        close(fd[0]);        
        _exit(0);
    }
    { int st; wait(&st); }

    /* Fils 2 : lit le 2e tiers (reprend là où fils 1 s'est arrêté) */
    p = fork();
    if(p < 0) die("fork");
    if(p == 0){
        read_exact_with_label(fd[0], t2, "fils2 (tiers 2)");
        close(fd[0]);
        _exit(0);
    }
    { int st; wait(&st); }

    /* Fils 3 : lit le dernier tiers (le reste) */
    p = fork();
    if(p < 0) die("fork");
    if(p == 0){
        read_exact_with_label(fd[0], t3, "fils3 (tiers 3)");
        close(fd[0]);
        _exit(0);
    }
    { int st; wait(&st); }

    /* Le père ferme la lecture et termine */
    close(fd[0]);
    return 0;
}
