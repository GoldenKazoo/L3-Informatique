#include <unistd.h>
#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <sys/wait.h>

int main(void)
{
    int p2f[2];
    int f2p[2];
    char buf[5];

    pipe(p2f);
    pipe(f2p);

    pid_t pid = fork();

    if (pid == -1)
    {
        perror("Fork error");
        return 1;
    }

    if (pid == 0)
    {
        close(p2f[1]);
        close(f2p[0]);

        read(p2f[0], buf, 5);
        printf("Le fils lit %s\n", buf);

        strcpy(buf, "pong");
        write(f2p[1], buf, 5);

        close(p2f[0]);
        close(f2p[1]);
    }
    else
    {
        close(p2f[0]);
        close(f2p[1]);

        strcpy(buf, "ping");
        write(p2f[1], buf, 5);

        read(f2p[0], buf, 5);
        printf("Le pere lit %s\n", buf);

        close(p2f[1]);
        close(f2p[0]);

        wait(NULL);
    }

    return 0;
}
