#include <stdlib.h>
#include <unistd.h>
#include <sys/stat.h>
#include <fcntl.h>
#include <stdio.h>


int main(void)
{
    mkfifo("in.fifo", 0666);
    mkfifo("out.fifo", 0666);
    int fd_in = open("in.fifo", O_RDONLY);
    int fd_out = open("out.fifo", O_WRONLY);

    char c;

    read(fd_in, &c, 1);
    printf("Jai lu %c.\n", c);
    c = c+1;
    write(fd_out, &(c), 1);
    printf("J'ai ecris %c", c);

}