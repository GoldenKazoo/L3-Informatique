#include <unistd.h>
#include <stdio.h>
#include <stdlib.h>
int main()
{
    char c;
    char buf[4];
    int fd[2];
    pipe(fd);
    
    buf[3] = '\0';
    pid_t pid = fork();
    if (pid == 0)
    {
        read(fd[0], buf, 3);
        printf("%s", buf);
        close(fd[0]);
    }
    else
    {
        c = 'a';
        write(fd[1], &c, 1);
        c = 'b';
        write(fd[1], &c, 1);
        c = 'c';
        write(fd[1], &c, 1);
        close(fd[1]);
    }

}