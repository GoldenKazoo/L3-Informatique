#include <fcntl.h>
#include <unistd.h>
#include <stdio.h>

int main() {
    int fd = open("monfichier.txt", O_RDWR);
    if (fd == -1) {
        perror("open");
        return 1;
    }
    lseek(fd, 10, SEEK_SET);
    if (ftruncate(fd, 100) == -1) {
        perror("ftruncate");
        close(fd);
        return 1;
    }
    
    printf("Fichier tronqué ou étendu à 100 octets\n");
    close(fd);
    return 0;
}
