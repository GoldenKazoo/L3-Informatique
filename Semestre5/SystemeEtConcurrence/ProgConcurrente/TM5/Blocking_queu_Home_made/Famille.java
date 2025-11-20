package Blocking_queu_Home_made;

import java.util.Random;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;


public class Famille implements Runnable
{
    int                     id;
    Blocking_queu          queu;
    Random                  r = new Random();

    public Famille (int id, Blocking_queu bq)
    {
        this.id = id;
        this.queu = bq;
    }

    public void run()
    {
        while (true)
        {
            this.queu.get();
            System.out.println("Le membre de la famille : " + id + " a pris une saucisse ! Nombre restant de saucisse : " + queu.queu.size());
            try {
                Thread.sleep(r.nextInt(500));

            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }        }
    }
}
