#include <unistd.h>
#include <stdlib.h>
#include <stdio.h>
#include <fcntl.h>



void write_buffer(int fd, const char *buf, ssize_t n);
int open_file(const char *filename);