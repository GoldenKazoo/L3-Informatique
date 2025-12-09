package TwoThread;

public class Main {
    public static void main(String[] args) {

        TwoThread executor = new TwoThread();

        // Envoi de 10 tâches pour visualiser le parallélisme
        for (int i = 0; i < 10; i++) {
            final int id = i;
            executor.execute(new Runnable() {
                @Override
                public void run() {
                    for (int k = 0; k < 5; k++) {
                        System.out.println("Tâche " + id + " → " + k +
                                           " exécuté par " + Thread.currentThread().getName());
                        try { Thread.sleep(300); }
                        catch (InterruptedException e) {}
                    }
                }
            });
        }
    }
}

