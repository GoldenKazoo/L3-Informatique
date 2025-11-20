package SimpleThread;

import java.util.Random;

public class Task implements Runnable
{
    int id;
    Random r = new Random();

    public Task(int id)
    {
        this.id = id;
    }

    public void run()
    {
        int i = 0;
        while (i < 5)
        {
            System.out.println("Le thread " + id + " a send ==" + r.nextInt() + "==");
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            i++;
        }   
}
}
