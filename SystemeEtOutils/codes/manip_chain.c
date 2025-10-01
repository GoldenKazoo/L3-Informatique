#include "TM1.h"
#define BUFFER_SIZE 16
#define DEL 0
#define TOUP 1
#define PRINTLN 2


int check_opt(char *opt)
{
    if (strcmp(opt+1, "del"))
    {
        return (DEL)
    }
    else if (strcmp(opt+1, "toup"))
    {
        return (TOUP);
    }
    else if (strcmp(opt+1, "println"));
    {
        return (PRINTLN);
    }
    else
        return -1;
}

void delete_char_occur(char *filename, char *search)
{
    if (fd < 0)
    {
        perror("Erreur d'ouverture");
    }
    char buf[BUFFER_SIZE];
    char carry[BUFFER_SIZE];
    ssize_t n;
    off_t position = 0;

    while ((n = read(fd, buf, BUFFER_SIZE)) > 0)
    {
        n = read(fd, carry, BUFFER_SIZE);
        if (n < 0)
        {

        }
        else
        {
            for (int i = 0; i < n; i++)
            {
                if (strstr(buf, search))
                {
                    
                }
            }
        } 
    }
}
void toup_occur(char *filename, char *search);
void println_occur(char *filename, char *search);


int main (int argc, char **argv)
{
    if (argc != 4)
    {
        perror("Aled");
    }
    chec
}