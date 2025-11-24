#include <stdlib.h>
#include <unistd.h>
#include <sys/stat.h>
#include <fcntl.h>
#include <stdio.h>


int main(void)
{
    char c;

    mkfifo("canal.fifo", 0666);
    int fd = open("canal.fifo", O_RDONLY);
    read(fd, &c, 1);
    printf("Jai lu %c.\n", c);
    close(fd);
    fd = open("canal.fifo", O_WRONLY);
    c = c+1;
    write(fd, &(c), 1);
    printf("J'ai ecris %c", c);
    close(fd);
}