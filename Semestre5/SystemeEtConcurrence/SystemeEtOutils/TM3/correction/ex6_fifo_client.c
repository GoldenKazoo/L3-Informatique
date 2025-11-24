
/*
 * Ex6 — Client avec deux FIFOs (in/out).
 * Envoie un caractère (par défaut 'a') et lit la réponse ('b').
 */
#include <sys/types.h>
#include <sys/stat.h>
#include <fcntl.h>
#include <unistd.h>
#include <stdio.h>
#include <stdlib.h>
#include <errno.h>

static void die(const char *msg){ perror(msg); exit(EXIT_FAILURE); }

int main(int argc, char **argv){
    char c = (argc > 1 && argv[1][0]) ? argv[1][0] : 'a';

    int fin  = open("in.fifo",  O_WRONLY);
    if(fin  == -1) die("open in.fifo WR");
    int fout = open("out.fifo", O_RDONLY);
    if(fout == -1) die("open out.fifo RD");

    if(write(fin, &c, 1) < 0) die("write");
    char resp;
    if(read(fout, &resp, 1) != 1) die("read");
    dprintf(STDOUT_FILENO, "%c -> %c\n", c, resp);

    close(fin);
    close(fout);
    return 0;
}
