public class Counter extends Thread
{
    int countTo;
    int id;

    public Counter (int value, int id)
    {
        this.countTo = value;
        this.id = id;
    }
    public void run ()
    {
        int i = 0;

        while (i <= countTo)
        {
            System.out.print("Je suis et ,on id c*est :" + this.id + "vOICI ,Q VQLUE");
            System.out.println(i);

            i++;
        }
    }
}