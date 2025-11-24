
/*
 * Ex6 — Serveur avec deux FIFOs (in.fifo pour recevoir, out.fifo pour répondre).
 * Protocole: client envoie un caractère, serveur répond le suivant (ex: 'a' -> 'b').
 */
#include <sys/types.h>
#include <sys/stat.h>
#include <fcntl.h>
#include <unistd.h>
#include <stdio.h>
#include <stdlib.h>
#include <errno.h>

static void die(const char *msg){ perror(msg); exit(EXIT_FAILURE); }

int main(void){
    if(mkfifo("in.fifo", 0666) == -1 && errno != EEXIST) die("mkfifo in");
    if(mkfifo("out.fifo", 0666) == -1 && errno != EEXIST) die("mkfifo out");

    int fin  = open("in.fifo",  O_RDONLY);
    if(fin  == -1) die("open in.fifo");
    int fout = open("out.fifo", O_WRONLY);
    if(fout == -1) die("open out.fifo");

    char c;
    ssize_t r = read(fin, &c, 1);
    if(r < 0) die("read");
    if(r == 0) { // EOF
        close(fin); close(fout);
        return 0;
    }

    char resp = (c == 'z') ? 'a' : (char)(c + 1);
    if(write(fout, &resp, 1) < 0) die("write");

    close(fin);
    close(fout);
    return 0;
}
