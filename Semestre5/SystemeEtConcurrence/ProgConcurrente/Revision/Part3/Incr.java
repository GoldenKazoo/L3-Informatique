import java.util.concurrent.locks.*;

public class Incr implements Runnable
{
    int x = 0;
    Lock lock = new ReentrantLock();
    public void run()
    {
        int i = 0;
            while (i < 10000)
            {
                lock.lock();
                try
                {
                    x++;
                }
                finally
                {
                i++;

                lock.unlock();

                }
            }
    }
        int getX()
    {
        return (this.x);
    }
    }