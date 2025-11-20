#include <unistd.h>
#include <stdlib.h>
#include <stdio.h>
#include <fcntl.h>

const char FILE_MSG[] = "Début du fichier\n";

int open_file(const char *filename) {
    int fd = open( /* TODO : filename, flags */ );
    if (fd < 0) {
        perror("open");
        exit(1);
    }
    return fd;
}

int main(int argc, char *argv[]) {
    if (argc != 2) {
        const char USAGE[] = "Usage: ./open_file fichier\n";
        write_buffer(STDERR_FILENO, USAGE, sizeof(USAGE)-1);
        return 1;
    }

    int fd = open_file(argv[1]);

    /* Écrire FILE_MSG dans le fichier */

    close(fd);

    return 0;
}