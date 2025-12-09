package TM6;
public interface MyFuture<V>
{
    boolean isDone();
    V get() throws InterruptedException;
}