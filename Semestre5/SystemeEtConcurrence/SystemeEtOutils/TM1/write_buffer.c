#include <unistd.h>
#include <stdlib.h>
#include <stdio.h>

#define BUF_SIZE 64
const char TEST_MSG[] = "Test write_buffer\n";

void write_buffer(int fd, const char *buf, ssize_t n) {
    ssize_t written = write( /* TODO : fd, buf, n */ );
    if (written != n) {
        perror("write");
        exit(1);
    }
}

int main() {
    /* Test : écrire TEST_MSG sur la sortie standard */    
    return 0;
}