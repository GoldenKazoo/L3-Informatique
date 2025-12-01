CC=gcc
CFLAGS=-Wall -Wextra -Werror -pedantic -std=c99

all: client server decoder

client: client.c
	$(CC) $(CFLAGS) client.c -o client

server: server.c
	$(CC) $(CFLAGS) server.c -o server

decoder: decoder.c
	$(CC) $(CFLAGS) decoder.c -o decoder

clean:
	rm -f client server decoder
