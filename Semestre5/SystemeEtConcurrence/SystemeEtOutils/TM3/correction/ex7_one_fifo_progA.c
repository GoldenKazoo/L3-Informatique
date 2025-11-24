// progA.c — Un seul FIFO partagé : écrire puis lire (alternance)
#include <sys/types.h>
#include <sys/stat.h>
#include <fcntl.h>
#include <unistd.h>
#include <stdio.h>
#include <stdlib.h>
#include <errno.h>

static void die(const char *m){ perror(m); exit(1); }

int main(void){
    const char *path = "canal.fifo";
    if (mkfifo(path, 0666) == -1 && errno != EEXIST) die("mkfifo");

    // Tour 1 : A écrit, B lit
    int w = open(path, O_WRONLY);            // bloque jusqu'à ce que B ouvre en lecture
    if (w == -1) die("open WR");
    char c = 'a';
    if (write(w, &c, 1) != 1) die("write");
    close(w);

    // Tour 2 : A lit, B écrit
    int r = open(path, O_RDONLY);            // bloque jusqu'à ce que B ouvre en écriture
    if (r == -1) die("open RD");
    char resp;
    if (read(r, &resp, 1) != 1) die("read");
    close(r);

    dprintf(STDOUT_FILENO, "A: %c -> %c\n", c, resp);
    return 0;
}
