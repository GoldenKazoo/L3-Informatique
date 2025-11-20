package Bar_bq;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingDeque;
import java.util.concurrent.BlockingQueue;

public class Main
{
    Famille[]   famille = new Famille[10];
    Daron[]     daron  = new Daron[10];

    

    BlockingQueue<String> bq = new ArrayBlockingQueue<>(10);

    public static void main(String[] args)
    {
        Famille[]   famille = new Famille[10];
        Daron[]     daron  = new Daron[10];

        BlockingQueue<String> bq = new ArrayBlockingQueue<>(10);



        for (int j = 0; j < 10; j++)
            {
                daron[j] = new Daron(j, bq);
                new Thread(daron[j]).start();
            }

        try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        for (int i = 0; i < 10; i++)
        {
            famille[i] = new Famille(i, bq);
            new Thread(famille[i]).start();
        }
    }
}
