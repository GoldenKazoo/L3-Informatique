#include <stdlib.h>
#include <unistd.h>
#include <sys/stat.h>
#include <fcntl.h>
#include <stdio.h>


int main()
{
    int p2f[2];
    int f2p[2];

    pipe(p2f);
    pipe(f2p);
    pid_t pid = fork();

    if (pid == 0)   //fils
    {
        while (true)
        {

        }
    }
    else
    {
        char begin = 'a';

        while(true)
        {

        }
    }
}