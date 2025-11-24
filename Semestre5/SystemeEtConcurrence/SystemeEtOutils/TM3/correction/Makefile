CC=gcc
CFLAGS=-Wall -Wextra -Werror -pedantic -std=c99

all: ex1 ex2 ex3 ex3_non_deterministe ex4 ex5_producer ex5_consumer ex6_server ex6_client ex7_A ex7_B ex8 ex9

ex1: ex1_pipe_basic.c
	$(CC) $(CFLAGS) $< -o $@

ex2: ex2_pingpong_two_pipes.c
	$(CC) $(CFLAGS) $< -o $@

ex3: ex3_multiple_readers.c
	$(CC) $(CFLAGS) $< -o $@

ex3_non_deterministe: ex3_non_deterministe.c
	$(CC) $(CFLAGS) $< -o $@

ex4: ex4_pipeline.c
	$(CC) $(CFLAGS) $< -o $@

ex5_producer: ex5_fifo_producer.c
	$(CC) $(CFLAGS) $< -o $@
ex5_consumer: ex5_fifo_consumer.c
	$(CC) $(CFLAGS) $< -o $@

ex6_server: ex6_fifo_server.c
	$(CC) $(CFLAGS) $< -o $@
ex6_client: ex6_fifo_client.c
	$(CC) $(CFLAGS) $< -o $@

ex7_A: ex7_one_fifo_progA.c
	$(CC) $(CFLAGS) $< -o $@
ex7_B: ex7_one_fifo_progB.c
	$(CC) $(CFLAGS) $< -o $@

ex8: ex8_llc_two_proc.c
	$(CC) $(CFLAGS) $< -o $@

ex9: ex9_llc_ring.c
	$(CC) $(CFLAGS) $< -o $@

clean:
	rm -f ex1 ex2 ex3 ex3_non_deterministe ex4 ex5_producer ex5_consumer ex6_server ex6_client ex7_A ex7_B ex8 ex9
	rm -f canal.fifo in.fifo out.fifo
