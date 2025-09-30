public class IncrementSync {
    private int x = 0;

    public synchronized void increment() {
        x++;
    }

    public int getValue() {
        return x;
    }

    public static void main(String[] args) throws InterruptedException {
        IncrementSync counter = new IncrementSync();

        Runnable task = () -> {
            for (int i = 0; i < 10000; i++) {
                counter.increment();
            }
        };

        Thread t1 = new Thread(task);
        Thread t2 = new Thread(task);

        t1.start();
        t2.start();
        t1.join();
        t2.join();

        System.out.println("Valeur finale : " + counter.getValue());
    }
}
