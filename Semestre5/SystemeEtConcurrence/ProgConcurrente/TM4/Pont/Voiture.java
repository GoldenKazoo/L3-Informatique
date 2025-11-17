package Pont;

public class Voiture extends Thread {
    private Pont pont;
    private int id;
    private boolean direction; // false = nord, true = sud

    public Voiture(Pont pont, int id, boolean direction) {
        this.pont = pont;
        this.id = id;
        this.direction = direction;
    }

    public void run() {
        try {
            while (true) {
                pont.entrer(id, direction);
                Thread.sleep(2000);
                pont.sortir(id);
                Thread.sleep(2000);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
