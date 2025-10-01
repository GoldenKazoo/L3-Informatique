#include "exo3.h"

int main (int argc, char **argv)
{
    int index;
    pid_t pid[argc - 2];

    index = 0;
    for (int i = 3; i < argc; i++)
    {

        pid_t pid = fork();
        pid[index] = pid;
        if (pid < 0)
        {
            perror("fork");
            return 1;
        }
        if (pid == 0)
        {
            execlp(argv[1], argv[i]);
            perror("execlp");

        }
        index++;
}

        for(int i = 0; i <= argc - 2; i++)
        {
            int status;

            status = 0;
            waitpid(pid[i], &status, 0);
            if (WIFEXITED(status))
            {
                printf("Fils %d terminé avec code %d\n", WIFEXITED() , WEXITSTATUS (status));
            }
        }
}