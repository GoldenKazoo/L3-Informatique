package Bar_bq;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class Daron implements Runnable
{
    int                     id;
    BlockingQueue<String>   queu;

    public Daron (int id, BlockingQueue<String> queu)
    {
        this.id = id;
        this.queu = queu;
    }

    public void run()
    {
        while (true)
        {
            try
            {
                this.queu.put("saucisse");
                System.out.println("=== Le daron : " + id + " a remis une saucisse sur le grill !===");
            }
            catch (InterruptedException e)
            {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }
}
