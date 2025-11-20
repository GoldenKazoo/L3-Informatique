#include <stdio.h>
#include <stdlib.h>
#include <fcntl.h>
#include <unistd.h>
#include <ctype.h>

void print_uppercase(const char *filename) {
    int fd = /* TODO : open(...)*/;
    if (fd < 0) { perror("open"); exit(1); }

    char buf[64];
    ssize_t n;
    while ((n = read(fd, buf, sizeof(buf))) > 0) {
        for (int i = 0; i < n; i++) {
            buf[i] = /* TODO : convertir en majuscule */;
        }
        /* TODO : écrire buf sur la sortie standard */
    }
    if (n < 0) { perror("read"); exit(1); }
    close(fd);
}