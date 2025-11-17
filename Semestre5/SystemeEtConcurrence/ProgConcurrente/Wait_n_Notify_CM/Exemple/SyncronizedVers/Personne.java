package Exemple.SyncronizedVers;

import java.util.Random;

public class Personne implements Runnable
{
    private Douche d;
    
    public Personne(Douche d)
    {
        this.d = d;
    }

    public void run()
    {
        while (true)
        {
            synchronized(d)
            {
                d.enter();
                System.out.println("La personne " + Thread.currentThread().getId() + " est dans la douche");
                try {
                    Thread.sleep(new Random().nextInt(500));
                } catch (InterruptedException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                d.exit();
            }
                try {
                    Thread.sleep(new Random().nextInt(500));
                } catch (InterruptedException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
        }
    }
}
