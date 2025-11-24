
/*
 * Ex5 — Producteur FIFO (named pipe): ecrit un message.
 * Notes:
 *  - mkfifo() peut échouer si le fichier existe déjà ; on tolère EEXIST.
 *  - O_WRONLY bloque tant qu'aucun lecteur n'a ouvert le FIFO pour lecture.
 */
#include <sys/types.h>
#include <sys/stat.h>
#include <fcntl.h>
#include <unistd.h>
#include <stdio.h>
#include <stdlib.h>
#include <errno.h>
#include <string.h>

static void die(const char *msg){ perror(msg); exit(EXIT_FAILURE); }

int main(void){
    const char *path = "canal.fifo";
    if(mkfifo(path, 0666) == -1 && errno != EEXIST) die("mkfifo");

    int fd = open(path, O_WRONLY);
    if(fd == -1) die("open WRONLY (attend un lecteur)");
    const char *msg = "Hello via FIFO!\n";
    if(write(fd, msg, strlen(msg)) < 0) die("write");
    close(fd);
    return 0;
}
