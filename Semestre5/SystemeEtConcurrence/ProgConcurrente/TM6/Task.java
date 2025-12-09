public class Task<V> implements Runnable
{
    Callable<V>     callable;
    FutureImpl<V>   future;

    public Task (Callable<V> call, FutureImpl<V> future)
    {
        this.callable = call;
        this.future = future;
    }
    
    @Override
    public void run()
    {
        try
        {
            V result = this.callable.call();
            future.set(result);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            future.set(null);
        }
    }
}
