public class Main()
{
    public static void main(args[])
    {
        Var v = new Var();
        Bonus b1 = new Bonus();
        Bonus b2 = new Bonus();
        Malus m1 = new Malus();
        Malus m2 = new Malus();

        b1.start();
        b2.start();
        m1.start();
        m2.start();
        print(v.x);
    }
}