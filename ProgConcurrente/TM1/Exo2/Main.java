public class Main{
    public static void main()
    {
        Counter counter1 = new Counter(1000, 1);
        Counter counter2 = new Counter(1000, 2);

        counter1.start();
        counter2.start();
    }
}