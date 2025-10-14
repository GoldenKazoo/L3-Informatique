import java.util.concurrent.*;
public class App
{

    public static void main(String args[])
    {
        Incr t = new Incr();
        Thread t1 = new Thread(t);
        Thread t2 = new Thread(t);

        t1.start();
        t2.start();
        try
        {
            t1.join();
            t2.join();
        }
        catch (InterruptedException e)
        {
            e.printStackTrace();
        }
        System.out.println(t.getX());
    }
}