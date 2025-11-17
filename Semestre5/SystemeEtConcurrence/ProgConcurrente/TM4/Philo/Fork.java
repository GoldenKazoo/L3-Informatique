package Philo;

public class Fork
{
    private boolean prise;

    public synchronized void prendre(int id)
    {
        while (prise)
        {
            try
            {
                wait();
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        prise = true;
        System.out.println(id + " prends une fourchette");
    }

    public synchronized void poser(int id)
    {
        prise = false;
        System.out.println(id + " pose une fourchette");
        notify();
    }
}
