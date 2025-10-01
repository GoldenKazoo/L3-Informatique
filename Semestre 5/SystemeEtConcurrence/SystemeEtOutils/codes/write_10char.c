#include <unistd.h>
#include <fcntl.h>
#include <stdlib.h>
#include <stdio.h>
#include "TM1.h"
#define BUF_SIZE 64
int main (int argc, char **argv)
{
    if (argc != 2) {
        const char USAGE[] = "Usage: ./write_10char fichier\n";
        write_buffer(STDERR_FILENO, USAGE, sizeof(USAGE)-1);
        return 1;
    }

    int fd = open(argv[1], O_RDONLY);
    char buf[BUF_SIZE];
    ssize_t n = read(fd, buf, BUF_SIZE);
    if (n < 0) {
        perror("read");
        close(fd);
        return 1;
    }
    write_buffer(1, buf, 10);
    lseek(fd, 0, SEEK_SET);
    n = read(fd, buf, BUF_SIZE);
    if (n < 0) {
        perror("read");
        close(fd);
        return 1;
    }
    write_buffer(1, buf, 10);
    close(fd);
    return 0;
}