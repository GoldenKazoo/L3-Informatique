#include "TM1.h"

void convert_file (const char * filename ) {
int fd = open(filename, O_RDWR);
if (fd < 0)
{
    perror("open"); exit (1);
}

char buf [64];
ssize_t n;
off_t pos = 0;

 while ((n = read(fd , buf , sizeof (buf))) > 0) {
for (int i = 0; i < n; i++) {
buf[i] = buf[i] + ('A' - 'a');
 }
 if (lseek (fd, -n, SEEK_CUR) < 0)
 { 
    perror("lseek");
    exit (1);
}
 if (write(fd, buf, 64))
 {
    perror("write");
    exit (1);
}
    pos += n;
 }

 if (n < 0) { perror("read"); exit (1); }
    close(fd);
 }

int main(int argc, char **argv)
{
    convert_file(argv[1]);
}