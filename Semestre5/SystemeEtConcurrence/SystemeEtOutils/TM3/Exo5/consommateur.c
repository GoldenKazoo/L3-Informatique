#include <stdlib.h>
#include <unistd.h>
#include <fcntl.h>
#include <stdio.h>

#define FIFO_PATH "canal.fifo"

int main(void)
{
    /* Ouvre le FIFO en lecture (bloquant) */
    int fd = open(FIFO_PATH, O_RDONLY);
    if (fd == -1) {
        perror("open reader");
        exit(EXIT_FAILURE);
    }

    char buf[128];
    ssize_t nread = read(fd, buf, sizeof(buf)-1);   // laisser place au '\0'
    if (nread == -1) {
        perror("read");
        close(fd);
        exit(EXIT_FAILURE);
    }
    buf[nread] = '\0';  /* terminateur */

    printf("[Consommateur] Message reçu : %s\n", buf);

    close(fd);
    return EXIT_SUCCESS;
}
