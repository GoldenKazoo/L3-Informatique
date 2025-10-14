import java.util.concurrent.locks.*;

public class App
{
    public static void main(String args[])
    {
        Lock firstlock = new ReentrantLock();
        Lock secondlock = new ReentrantLock();

        Thread t1 = new Thread (new MyRunnable("Thread 1", firstlock, secondlock));
        Thread t2 = new Thread (new MyRunnable("Thread 2", secondlock, firstlock));

        t1.start();
        t2.start();
    }
}