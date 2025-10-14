public class Question2 {
    public static void main(String[] args) throws InterruptedException {
        Compteur compteur = new Compteur(10000);
        Thread[] threads = new Thread[50];

        for (int i = 0; i < 50; i++) {
            threads[i] = new Thread(compteur);
            threads[i].start();
        }

        for (int i = 0; i < 50; i++) {
            threads[i].join();
        }

        System.out.println("Valeur finale : " + compteur.getX());  // 50 * 10000 = 500000
    }
}
