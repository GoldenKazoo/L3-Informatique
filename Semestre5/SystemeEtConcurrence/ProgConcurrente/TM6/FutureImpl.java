import java.util.Optional;

public class FutureImpl<V> implements MyFuture
{
    Optional<V> opt = Optional.empty();

    public synchronized void set(V val)
    {
        if (opt.isEmpty())
        {
            opt = Optional.of(val);
            notifyAll();
        }
    }
    
    public synchronized V get() throws InterruptedException
    {
        while (opt.isEmpty())
        {
            wait();
        }
        return (opt.get());
    }

    public synchronized boolean isDone()
    {
        return (opt.isPresent());
    }
    
}