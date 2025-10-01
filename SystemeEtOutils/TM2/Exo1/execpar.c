#include "exo1.h"

int main (int argc, char **argv)
{
    for (int i = 2; i < argc; i++)
    {
        pid_t pid = fork();

        if (pid == 0)
        {
            execlp(argv[1], argv[i]);
        }
    }
    for (int i = 2; i < argc; i++)
    {
        wait(NULL);
    }
}