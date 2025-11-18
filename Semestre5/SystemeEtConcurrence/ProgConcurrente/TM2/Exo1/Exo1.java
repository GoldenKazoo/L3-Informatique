import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

class Compteur implements Runnable {
    private int x = 0;
    private Lock lock = new ReentrantLock();
    private int repetitions;

    public Compteur(int repetitions) {
        this.repetitions = repetitions;
    }

    @Override
    public void run() {
        for (int i = 0; i < repetitions; i++) {
            lock.lock();
            try {
                x++;
            } finally {
                lock.unlock();
            }
        }
    }

    public int getX() {
        return x;
    }
}

public class Exo1 {
    public static void main(String[] args) throws InterruptedException {
        Compteur compteur = new Compteur(10000);

        Thread t1 = new Thread(compteur);
        Thread t2 = new Thread(compteur);

        t1.start();
        t1.join();

        t2.start();
        t2.join();

        System.out.println("Valeur finale : " + compteur.getX());
    }
}
