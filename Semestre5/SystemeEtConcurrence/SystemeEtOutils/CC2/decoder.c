#include <unistd.h>
#include <ctype.h>

static char decrypt_char(char c)
{
    if (c == 'a')
        return ('z');\
    if (c == 'A')
        return ('Z');
    if (c >='a' && c <='z')
        return (c - 1);
    if (c >= 'A' && c <= 'Z')
        return (c -1);
    return (c);
}

int main(void)
{
    char buf[256];
    char c;
    int read_bytes;

    read_bytes = 0;
    while (true)
    {
        read_bytes = read(STDIN_FILENO, buf, 256);
        for (int i = 0; i < read_bytes; i++)
        {
            c = decrypt_char(buf[i]);
            write(1, &c, 1);
        }
    }
    return 0;
}
