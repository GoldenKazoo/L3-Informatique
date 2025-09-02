public class Main()
{
    public static void main(String args[])
    {
        Counter counter1 = new Counter(1000);
        Counter counter2 = new Counter(1000);

        counter1.start();
        counter2.start();
    }
}