package Pont;

public class Pont {
    private boolean direction; // false = nord, true = sud
    private int nb_voiture = 0;
    private int attente_oppose = 0;

    public synchronized void entrer(int id, boolean going) {
        if (direction != going) {
            attente_oppose++;
        }

        while (nb_voiture > 0 && direction != going) {
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        if (direction != going) {
            direction = going;
        }

        nb_voiture++;
        if (attente_oppose > 0) attente_oppose--;

        System.out.println(id + " monte sur le pont (" + (going ? "sud" : "nord") + ")");
    }

    public synchronized void sortir(int id) {
        nb_voiture--;
        System.out.println(id + " quitte le pont (" + (direction ? "sud" : "nord") + ")");
        notifyAll();
    }
}
