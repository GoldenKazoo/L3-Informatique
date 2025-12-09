package TwoThread;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Executor;

public class TwoThread implements Executor
{
    BlockingQueue<Runnable> queu = new ArrayBlockingQueue<>(5);
    Thread fil1;
    Thread fil2;

    public Thread oneFil(Thread t)
    {
            
        t = new Thread(() ->
        {
            while (true)
            {

            
            try {

                    Runnable task = queu.take();
                    task.run();
                    Thread.sleep(0);
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }
    );
    t.start();
    return t;
    }

    public TwoThread()
    {
        fil1 = oneFil(fil1);
        fil2 = oneFil(fil2);
    }

    public void execute(Runnable r)
    {
        try
        {
            queu.put(r);
            System.out.println("Tâche ajoutée — queue size = " + queu.size());
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
