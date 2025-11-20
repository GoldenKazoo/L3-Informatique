package Blocking_queu_Home_made;

import java.util.concurrent.BlockingQueue;

public class Daron implements Runnable
{
    int                     id;
    Blocking_queu   queu;

    public Daron (int id, Blocking_queu bq)
    {
        this.id = id;
        this.queu = bq;
    }

    public void run()
    {
        while (true)
        {
            this.queu.put("saucisse");
            System.out.println("=== Le daron : " + id + " a remis une saucisse sur le grill !===");
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }
}
