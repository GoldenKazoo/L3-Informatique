#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>
#include <fcntl.h>
#include <sys/wait.h>
#include <string.h>

static char encrypt_char(char c)
{
    if (c == 'a')
        return ('z');
    if (c == 'A')
        return ('Z');
    if (c >= 'a' && c <= 'z')
        return (c + 1);
    if (c >= 'A' && c <= 'Z')
        return (c +1);
    return (c);
}
// TODO: implement encryption (+1 shift)
static void encrypt_message(char *s)
{
    int i = 0;

    while (s[i] != '\0')
    {
        s[i] = encrypt_char(s[i]);
        i++;
    }
}

int main(int argc, char *argv[])
{
    pid_t   pid;
    char *message_chiffre;
    // TODO: parse arguments ./client <fifo> <N> "<message>"
    if (argc != 4)
    {
        perror("Bad usage of command");
    }
    mkfifo("fifo", 0666);
    pid = fork();
    if (pid == 0)
    {
        int fd;

        message_chiffre = strcpy(argv[3]);
        encrypt_message(message_chiffre);
        snprintf (buffer , sizeof buffer , "%d: %s\n", getpid (), message_chiffre);
        fd = open(fifo, O_WRONLY);
        write (fd, message_chiffre, sizeof(char) * strlen(message_chiffre));
    }
    // TODO: fork N children
    // TODO: each child encrypts and writes to FIFO
    // TODO: parent waits all children
    return 0;
}
