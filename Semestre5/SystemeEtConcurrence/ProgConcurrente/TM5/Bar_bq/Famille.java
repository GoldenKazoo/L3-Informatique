package Bar_bq;

import java.util.Random;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class Famille implements Runnable
{
    int                     id;
    BlockingQueue<String>   queu;
    Random                  r = new Random();

    public Famille (int id, BlockingQueue<String> queu)
    {
        this.id = id;
        this.queu = queu;
    }

    public void run()
    {
        while (true)
        {
            try {
                this.queu.take();
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            System.out.println("Le membre de la famille : " + id + " a pris une saucisse ! Nombre restant de saucisse : " + queu.size());
            try {
                Thread.sleep(r.nextInt(500));

            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }        }
    }
}
