package Exemple.SyncronizedVers;

public class Douche
{
    int x = 0;

    public void enter()
    {
       x++;
       System.out.println("Dans la douche il y a :" + x); 
    }

    public void exit()
    {
        x--;
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