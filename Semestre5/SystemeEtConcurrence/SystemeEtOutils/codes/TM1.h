#ifndef TM1_H
#define TM1_H

#include <unistd.h>
#include <fcntl.h>
#include <stdlib.h>
#include <stdio.h>

#define BUF_SIZE 64

void write_buffer(int fd, const char *buf, size_t count);
void write_10char(int fd, const char *buf);

#endif /* TM1_H */