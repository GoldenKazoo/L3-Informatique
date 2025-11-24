// progB.c — Un seul FIFO partagé : lire puis écrire (alternance)
#include <sys/types.h>
#include <sys/stat.h>
#include <fcntl.h>
#include <unistd.h>
#include <stdio.h>
#include <stdlib.h>

static void die(const char *m){ perror(m); exit(1); }

int main(void){
    const char *path = "canal.fifo";

    // Tour 1 : B lit ce que A écrit
    int r = open(path, O_RDONLY);            // bloque jusqu'à ce que A ouvre en écriture
    if (r == -1) die("open RD");
    char c;
    if (read(r, &c, 1) != 1) die("read");
    close(r);

    // Tour 2 : B répond en écrivant la lettre suivante
    int w = open(path, O_WRONLY);            // bloque jusqu'à ce que A ouvre en lecture
    if (w == -1) die("open WR");
    char resp = (c == 'z') ? 'a' : (char)(c + 1);
    if (write(w, &resp, 1) != 1) die("write");
    close(w);

    dprintf(STDOUT_FILENO, "B: %c -> %c\n", c, resp);
    return 0;
}
