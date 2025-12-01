#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>
#include <fcntl.h>
#include <sys/stat.h>
#include <string.h>
#include <sys/wait.h>

// TODO: relay FIFO -> decoder -> stdout+log
int main(int argc, char *argv[]) {
    // TODO: parse ./server <fifo> <logfile>
    // TODO: mkfifo if needed
    // TODO: open FIFO for reading (blocking)
    // TODO: create pipes
    // TODO: fork and exec ./decoder
    // TODO: forward data and duplicate output with dup2
    // TODO: implement writer-factice
    return 0;
}
