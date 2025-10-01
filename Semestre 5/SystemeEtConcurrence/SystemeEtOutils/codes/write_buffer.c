#include <unistd.h>
#include <stdlib.h>
#include <stdio.h>
#include <fcntl.h>
#include <unistd.h>

#define BUF_SIZE 64
const char TEST_MSG[] = "Test write_buffer\n";

       /* TODO : écrire buf sur la sortie standard */
void write_buffer(int fd, const char *buf, ssize_t n) {
    ssize_t written = write(fd, buf, n);
    if (written != n) {
        perror("write");
        exit(1);
    }
}

int main(int argc, char **argv)
{
    char buf[BUF_SIZE];
    int fd = open(argv[1], O_RDONLY);
    if (fd < 0) {
        perror("open");
        return 1;
    }
    ssize_t n = read(fd, buf, BUF_SIZE);
    write_buffer(1, buf, n);
    close(fd);
    return 0;
}


char *nom;
char nom[];







open(fichier.txt, flag)




char *buf = malloc(sizeof(char) * )