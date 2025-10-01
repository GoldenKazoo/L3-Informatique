#include <stdio.h>
#include <stdlib.h>
#include <fcntl.h>
#include <unistd.h>
#include <ctype.h>

void print_uppercase(const char *filename) {
    int fd = open(filename, O_RDONLY);
    if (fd < 0) { perror("open"); exit(1); }

    char buf[64];
    ssize_t n;
    while ((n = read(fd, buf, sizeof(buf))) > 0) {
        for (int i = 0; i < n; i++) {
            buf[i] = buf[i] + ('A' - 'a');
        }
        write_buffer(STDOUT_FILENO, buf, n);
    }
    if (n < 0) { perror("read"); exit(1); }
    close(fd);
}