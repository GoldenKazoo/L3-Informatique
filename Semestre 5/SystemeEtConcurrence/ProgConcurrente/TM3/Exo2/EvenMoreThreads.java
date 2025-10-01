public class EvenMoreThreads {
    private int x = 0;

    public synchronized void increment() 
    {
        x++;
    }

    public int getValue() 
    {
        return x;
    }

    public static void main(String[] args) throws InterruptedException 
    {
        EvenMoreThreads counter = new EvenMoreThreads();

        Runnable task = new Runnable()
        {
            @Override
            public void run() {
            for (int i = 0; i < 10000; i++)
            {
                counter.increment();
            }
        }
        };


        Thread[] threads = new Thread[50];
        for (int i = 0; i < 50; i++) {
            threads[i] = new Thread(task);
            threads[i].start();
        }

        for (int i = 0; i < 50; i++) {
            threads[i].join();
        }

        System.out.println("Valeur finale : " + counter.getValue());
    }
}
