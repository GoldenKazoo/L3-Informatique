#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>
#include <sys/wait.h>
#include <string.h>

int main(void)
{
    int fd[2];
    if (pipe(fd) == -1)
    {
        perror("pipe");
        exit(1);
    }

    const char *text = "I am the bone of my sword, Steel is my body and fire is my blood, I have created over a thousand blades,  Unknown to Death, Nor known to Life. Have withstood pain to create many weapons.  Yet, those hands will never hold anything  So as I pray, Unlimited Blade Works.  ";
    write(fd[1], text, strlen(text));

    close(fd[1]);
    int iter = (strlen(text) / 64) + 1;
    for (int i = 0; i < iter; i++)
    {
        pid_t pid = fork();
        if (pid == 0) {
            close(fd[1]);
            char buffer[64];
            ssize_t n = read(fd[0], buffer, sizeof(buffer) - 1);
            if (n > 0) {
                buffer[n] = '\0';
                printf("Fils %d a lu: %s \n", i, buffer);
            } else {
                printf("Fils %d n'a rien lu.\n", i);
            }
            close(fd[0]);
            _exit(0);
        }
    }

    close(fd[0]);

    for (int i = 0; i < 3; i++)
        wait(NULL);

    return 0;
}
