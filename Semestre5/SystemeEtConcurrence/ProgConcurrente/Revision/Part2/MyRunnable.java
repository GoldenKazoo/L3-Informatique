import java.util.concurrent.locks.*;

public class MyRunnable implements Runnable
{
    String name;
    Lock lockRunA;
    Lock lockRunB;

    public MyRunnable (String name, Lock lockA, Lock lockB)
    {
        this.name = name;
        this.lockRunA = lockA;
        this.lockRunB = lockB;
    }
    public void run()
    {
        lockRunA.lock();
        System.out.println("Le thread " + name + " a pris le premier lock");
        try
        {
           Thread.sleep(1000);
        }
        catch(InterruptedException e)
        {
            e.printStackTrace();
        }
        lockRunB.lock();
        System.out.println("Le thread" + name + "a pris le deuxieme lock");

        lockRunB.unlock();
        lockRunA.unlock();
    }
}