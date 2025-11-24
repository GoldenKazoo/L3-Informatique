
/*
 * Ex5 — Consommateur FIFO: lit et affiche un message.
 * Ouvre le FIFO en lecture ; l'open() bloquera tant qu'aucun écrivain n'est présent.
 */
#include <sys/types.h>
#include <sys/stat.h>
#include <fcntl.h>
#include <unistd.h>
#include <stdio.h>
#include <stdlib.h>

static void die(const char *msg){ perror(msg); exit(EXIT_FAILURE); }

int main(void){
    const char *path = "canal.fifo";
    int fd = open(path, O_RDONLY);
    if(fd == -1) die("open RDONLY (attend un écrivain)");
    char buf[256];
    ssize_t r = read(fd, buf, sizeof(buf));
    if(r < 0) die("read");
    write(STDOUT_FILENO, buf, (size_t)r);
    close(fd);
    return 0;
}
