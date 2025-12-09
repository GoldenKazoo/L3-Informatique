public class MainTest {
    public static void main(String[] args) throws InterruptedException {

        TwoThreadsPool pool = new TwoThreadsPool();

        // Soumettre des tâches qui retournent un entier
        MyFuture<Integer> f1 = pool.submit(() -> {
            Thread.sleep(500);
            System.out.println("Tâche 1 terminée par " + Thread.currentThread().getName());
            return 10;
        });

        MyFuture<Integer> f2 = pool.submit(() -> {
            Thread.sleep(300);
            System.out.println("Tâche 2 terminée par " + Thread.currentThread().getName());
            return 20;
        });

        // Soumettre une tâche qui retourne une chaîne
        MyFuture<String> f3 = pool.submit(() -> {
            Thread.sleep(400);
            System.out.println("Tâche 3 terminée par " + Thread.currentThread().getName());
            return "Hello Pool!";
        });

        // Soumettre plusieurs petites tâches pour tester le flux parallèle
        for (int i = 4; i <= 8; i++) {
            final int id = i;
            pool.submit(() -> {
                for (int j = 0; j < 3; j++) {
                    System.out.println("Tâche " + id + " → " + j + " exécutée par " + Thread.currentThread().getName());
                    Thread.sleep(200);
                }
                return id * 2;
            });
        }

        // Récupération des résultats des premières tâches
        System.out.println("Résultat f1 : " + f1.get());
        System.out.println("Résultat f2 : " + f2.get());
        System.out.println("Résultat f3 : " + f3.get());

        System.out.println("Toutes les tâches soumises !");
    }
}
