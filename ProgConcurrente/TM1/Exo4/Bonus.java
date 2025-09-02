public class Bonus extends Thread()
{
    public void run()
    {
        int i = 0;
        while (i < 10)
        {
            v.x++;
            i++;
        }
    }
}