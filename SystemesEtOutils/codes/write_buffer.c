#include <unistd.h>
#include <stdlib.h>
#include <stdio.h>

#define BUF_SIZE 64
const char TEST_MSG[] = "Test write_buffer\n";

void write_buffer(int fd, const char *buf, ssize_t n) {
    ssize_t written = write(fd, buf, n);
    if (written != n) {
        perror("write");
        exit(1);
    }
}

// int main() {
//     write_buffer(1, TEST_MSG, BUF_SIZE); //Ici le texte depasse la taille du buffer size, attention ca marche mais il peut y avoir des comportements bizarre
//     return 0;
// }