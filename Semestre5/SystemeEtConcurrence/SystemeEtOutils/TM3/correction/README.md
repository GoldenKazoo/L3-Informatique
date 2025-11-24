
# TM3 — Tubes et FIFOs : corrections commentées

Ce pack contient des corrections **minimales mais robustes** pour chaque exercice,
avec des **commentaires pédagogiques** dans le code (gestion des erreurs, fermeture
des descripteurs, protocole d’échange, pièges classiques).

## Contenu

- `ex1_pipe_basic.c` — Tube anonyme, un premier échange parent→fils.
- `ex2_pingpong_two_pipes.c` — Dialogue bidirectionnel avec deux tubes.
- `ex3_multiple_readers.c` — Plusieurs lecteurs concurrentiels sur le même tube.
- `ex4_pipeline.c` — Chaînage `ls | grep .c | wc -l` avec `dup2` et 2 tubes.
- `ex5_fifo_producer.c`, `ex5_fifo_consumer.c` — Producteur/consommateur via FIFO.
- `ex6_fifo_server.c`, `ex6_fifo_client.c` — Aller/retour via deux FIFOs (in/out).
- `ex7_one_fifo_progA.c`, `ex7_one_fifo_progB.c` — Dialogue sur **un seul** FIFO partagé.
- `ex8_llc_two_proc.c` — « Large Letter Collider » à 2 processus (aller/retour).
- `ex9_llc_ring.c` — LLC généralisé avec un anneau de `n` processus.

## Compilation (exemples)

```sh
gcc -Wall -Wextra -Werror -pedantic -std=c99 ex1_pipe_basic.c -o ex1
```

Pour les FIFOs, pensez à nettoyer :
```sh
rm -f canal.fifo in.fifo out.fifo
```

## Remarques
- Tous les programmes **ferment systématiquement** les descripteurs inutiles.
- Les écritures de **taille ≤ PIPE_BUF** sont atomiques dans un FIFO/pipe.
- Pour un **FIFO unique bidirectionnel**, on ouvre souvent en `O_RDWR` des deux côtés
  pour éviter les blocages à `open()` et pour permettre lecture/écriture alternées.
