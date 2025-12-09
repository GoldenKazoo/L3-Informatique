package TM6;

public class Task<V> implements Runnable
{
    Callable<V> callable;
    FuturImpl<V> future;

    public Task(Callable<V> call, FuturImpl<V> future)
    {
        this.callable = call;
        this.future = future;
    }
    
    @Override
    public void run()
    {
       try
       {
        V val = callable.call();
        future.set(val);
       }
       catch (Exception e)
       {
        future.set(null);
       } 
    }
}
