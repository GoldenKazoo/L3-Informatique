public class MyRunnable implements Runnable
{
    public void run()
    {
        System.out.println("Hey c'est moi :" + Thread.currentThread().getName());
    }
}