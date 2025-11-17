package Exemple.WaitNotifyVers;

public class Douche
{
    int x = 0;

    public synchronized void enter()
    {
            if (x >= 2)
            {
                try {
                    this.wait();
                } catch (InterruptedException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
            x++;
            System.out.println("Dans la douche il y a : " + x + " personnes");
        } 

    public synchronized void exit()
    {
            x--;
            notify();
    }

    public static void main(String[] args)
    {
        Douche d = new Douche();
        Personne p = new Personne(d);
        new Thread(p).start();
        new Thread(p).start();
        new Thread(p).start();
        new Thread(p).start(); 
        new Thread(p).start();
        new Thread(p).start(); 
        new Thread(p).start();
        new Thread(p).start(); 
        new Thread(p).start();
        new Thread(p).start(); 
    }
}