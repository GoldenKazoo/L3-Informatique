#include <stdlib.h>
#include <unistd.h>
#include <sys/stat.h>
#include <fcntl.h>
#include <stdio.h>

int main (int argc, char **argv)
{
    char c = argv[1][0];

    int fd_in = open("in.fifo", O_WRONLY);
    int fd_out = open("out.fifo", O_RDONLY);

    write(fd_in, &c, 1);
    printf("J'ai envoye %c.\n", c);
    read(fd_out, &c, 1);
    printf("J'ai lu %c.", c);
    close(fd_in);
    close(fd_out);
}