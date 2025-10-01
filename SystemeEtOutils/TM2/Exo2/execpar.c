#include "exo2.h"

int main (int argc, char **argv)
{
    int running = 0;
    int max_parallel = atoi(argv[2]);

    for (int i = 3; i < argc; i++)
    {
        while(running >= max_parallel)
        {
            wait(NULL);
            running--;
        }

        pid_t pid = fork();

        if (pid == 0)
        {
            execlp(argv[1], argv[i]);
        }
        running++;
        // waitpid(pid, 0, 0);
    }
    for (int i = 2; i < argc; i++)
    {

        wait(NULL);
        running--;
    }
}