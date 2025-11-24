#include <stdlib.h>
#include <unistd.h>
#include <sys/stat.h>
#include <fcntl.h>
#include <stdio.h>

int main (int argc, char **argv)
{
    char c = argv[1][0];

    int fd = open("canal.fifo", O_WRONLY);

    write(fd, &c, 1);
    printf("J'ai envoye %c.\n", c);
    close(fd);
    fd = open("canal.fifo", O_RDONLY);
    read(fd, &c, 1);
    printf("J'ai lu %c.", c);
    close(fd);
}