package TM6;

import java.util.Optional;

public class FuturImpl<V> implements MyFuture
{
    Optional<V> opt;

    public synchronized void set(V val)
    {
        this.opt = Optional.of(val);
        notifyAll();
    }

    public synchronized V get()
    {
        while (opt.isEmpty())
        {
            try
            {
                wait();
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        return (opt.get());
    }

    public boolean isDone()
    {
        return (opt.isPresent());
    }
}