public class Counter extends Threads
{
    int countTo;

    public Counter (int value)
    {
        this.countTo = value;
    }
    public run ()
    {
        int i = 0;

        while (i <= countTo)
        {
            println(i);
        }
    }
}