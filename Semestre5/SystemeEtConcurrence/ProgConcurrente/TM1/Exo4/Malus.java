public class Malus extends Thread()
{
    public Malus (Var var)
    {
        
    }
    public void run()
    {
        int i = 0;
        while (i < 10)
        {
            v.x--;
            i++;
        }
    }
}