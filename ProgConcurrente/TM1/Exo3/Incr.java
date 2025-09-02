public class Incr extends Thread()
{
    public void run()
    {
        int i = 0;

        while (i < 10)
        {
            v.x++;
            i++;
        }
        println("I printed 10 times !");
    }
}