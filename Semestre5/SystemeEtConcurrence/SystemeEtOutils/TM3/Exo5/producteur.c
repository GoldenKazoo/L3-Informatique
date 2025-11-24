#include <stdlib.h>
#include <unistd.h>
#include <sys/stat.h>
#include <fcntl.h>
#include <stdio.h>

#define FIFO_PATH "canal.fifo"

int main(void)
{
    /* Crée le FIFO s’il n’existe pas déjà */
    if (mkfifo(FIFO_PATH, 0666) == -1) {
        perror("mkfifo");
        /* on continue même si le fichier existe déjà */
    }

    /* Ouvre le FIFO en écriture (bloquant) */
    int fd = open(FIFO_PATH, O_WRONLY);
    if (fd == -1) {
        perror("open writer");
        exit(EXIT_FAILURE);
    }

    /* Écrit 5 octets ("Coucou") */
    const char *msg = "Coucou";
    ssize_t nwritten = write(fd, msg, 6);   // +1 pour le \0 si vous voulez l’envoyer
    if (nwritten == -1) {
        perror("write");
        close(fd);
        exit(EXIT_FAILURE);
    }
    printf("[Producteur] %zd octets écrits.\n", nwritten);

    close(fd);      /* libère le descripteur */
    return EXIT_SUCCESS;
}
