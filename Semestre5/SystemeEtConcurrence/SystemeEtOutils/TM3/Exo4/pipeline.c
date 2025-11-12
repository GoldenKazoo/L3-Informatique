#include <unistd.h>
#include <stdio.h>
#include <stdlib.h>
#include <sys/wait.h>

int main()
{
    int fd1[2];
    int fd2[2];


    if (pipe(fd1) == -1 || pipe(fd2) == -1)
    {
        return 1;
    }

    pid_t pid1 = fork();
    
    if (pid1 == 0)
    {
        dup2(fd1[1], STDOUT_FILENO);

        execlp("ls", "ls");
        close(fd1[1]);
    }

    pid_t pid2 = fork();

    if (pid2 == 0)
    {
        dup2(fd1[0], STDIN_FILENO);
        dup2(fd2[1], STDOUT_FILENO);
        // close(fd1[0]);
        close(fd2[0]);
        execlp("grep", "grep", ".c");
    }

    pid_t pid3 = fork();

    if (pid3 == 0)
    {
        dup2(fd2[0], STDIN_FILENO);
        close(fd2[0]);
        execlp("wc", "wc", "-l"); 
    }
}