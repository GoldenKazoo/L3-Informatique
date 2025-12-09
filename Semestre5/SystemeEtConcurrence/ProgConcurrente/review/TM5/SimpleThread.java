import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Executor;

public class SimpleThread implements Executor
{
    Thread t1;
    BlockingQueue<Runnable> queu = new ArrayBlockingQueue<>(5);

    public SimpleThread()
    {
        t1 = work(t1);
    }

    private Thread work(Thread t)
    {
        t = new Thread(() -> 
    {
        while (true)
        {
            try
            {
                Runnable task = queu.take();
                task.run();
            }
            catch (Exception e)
            {
    
            }
        }
    });
    t.start();
    return t;
    }

    public void execute(Runnable task)
    {
        try
        {
            queu.put(task);
        }
        catch (Exception e)
        {

        }
    }
}
