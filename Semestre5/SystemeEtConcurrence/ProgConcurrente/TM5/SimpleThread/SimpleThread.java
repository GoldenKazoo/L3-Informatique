package SimpleThread;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Executor;

public class SimpleThread implements Executor
{
    Thread fil;
    BlockingQueue<Runnable>  queu = new ArrayBlockingQueue<>(5);

    public SimpleThread()
    {
        fil = new Thread(() ->
        {
            while (true)
            {
                try
                {
                    Runnable task = queu.take();
                    task.run();
                } catch (InterruptedException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        });
        fil.start();
    }
    
    public void execute(Runnable task)
    {
        try
        {
            queu.put(task);
            System.out.println("=== Elements dans la queu : " + queu.size()+ "===");
    
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        try {
            Thread.sleep(0);
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
