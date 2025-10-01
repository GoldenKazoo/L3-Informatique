#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>

int main(int argc, char *argv[]) {
    if(argc != 2) {
        fprintf(stderr, "Usage: %s <nombre>\n", argv[0]);
        return 1;
    }

    int n = atoi(argv[1]);
    for(int i = n; i > 0; i--) {
        printf("[%d] %d\n", getpid(), i);
        fflush(stdout); // forcer l'affichage immédiat
        sleep(1);
    }

    printf("[%d] Fini!\n", getpid());
    return 0;
}