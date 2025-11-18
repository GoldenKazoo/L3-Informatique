package CleanHammam;

public class Hammam {

    private int size = 4;
    private int places_libre = 4;
    private int lavage = 12;
    private boolean nettoyageEnCours = false;

    public void enter(int id) {
        synchronized (this) {

            while (places_libre == 0 || nettoyageEnCours) {
                System.out.println("Client " + id + " attend...");
                try {
                    wait();
                } catch (InterruptedException e) {}
            }

            places_libre--;
            lavage--;
            System.out.println(" === Douche restantes avant lavage : " + lavage + " ===");

            System.out.println("Client " + id + " entre. Places : " + places_libre);

            if (lavage == 0) {
                nettoyageEnCours = true;
                notifyAll();
            }
        }

        prendre_bain(id);

        synchronized (this) {
            places_libre++;
            System.out.println("Client " + id + " sort. Places : " + places_libre);

            if (places_libre == size && nettoyageEnCours) {
                notifyAll();
            }

            notifyAll();
        }
    }

    private void prendre_bain(int id) {
        System.out.println("Client " + id + " prend un bain...");
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {}
    }

    public void clean_hammam() {
        synchronized (this) {
            while (!nettoyageEnCours) {
                try {
                    wait();
                } catch (InterruptedException e) {}
            }

            while (places_libre != size) {
                System.out.println("Agent attend que le hammam se vide...");
                try {
                    wait();
                } catch (InterruptedException e) {}
            }

            System.out.println("=== Ménage en cours... ===");
        }

        try {
            Thread.sleep(4000);
        } catch (InterruptedException e) {}

        synchronized (this) {
            lavage = 12;
            nettoyageEnCours = false;
            System.out.println("=== Ménage terminé ! ===");
            notifyAll();
        }
    }
}
