import java.util.Random;

class Pont {
    private boolean directionPont;       // sens actuel du pont : true = haut, false = bas
    private int voituresSurPont = 0;     // nombre de voitures actuellement sur le pont

    public synchronized void entrer(int id, boolean going) {
        // Si le pont est occupé dans le sens opposé, attendre
        while (voituresSurPont > 0 && directionPont != going) {
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        // Changer le sens uniquement si le pont est vide
        if (voituresSurPont == 0) {
            directionPont = going;
        }

        voituresSurPont++;
        System.out.println("Voiture " + id + " monte sur le pont. Direction: " + (going ? "haut" : "bas") +
                           " | Sur le pont: " + voituresSurPont);

        try {
            Thread.sleep(5000); // temps de traversée
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        voituresSurPont--;
        System.out.println("Voiture " + id + " quitte le pont. Direction: " + (going ? "haut" : "bas") +
                           " | Sur le pont: " + voituresSurPont);

        notifyAll(); // réveiller les voitures en attente
    }
}

class Voiture implements Runnable {
    int id;
    Pont pont;
    boolean road;
    private Random r = new Random();

    public Voiture(int id, Pont pont, boolean road) {
        this.id = id;
        this.pont = pont;
        this.road = road;
    }

    public void run() {
        while (true) { // simulation continue
            pont.entrer(id, road);
            try {
                Thread.sleep(r.nextInt(200)); // pause aléatoire avant de revenir
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}

public class PontSimulation {
    public static void main(String[] args) {
        Pont pont = new Pont();
        int nombreVoitures = 6; // nombre total de voitures
        Voiture[] voitures = new Voiture[nombreVoitures];

        for (int i = 0; i < nombreVoitures; i++) {
            // voitures avec ID pair vont vers le haut, impair vers le bas
            voitures[i] = new Voiture(i, pont, i % 2 == 0);
            new Thread(voitures[i]).start();
        }
    }
}
