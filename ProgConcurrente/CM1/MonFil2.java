class Monfil2 extends Thread
{
    public void run()
    {
        print("nana");
        print("nana");
    }
}

void main ()
{
    MonFil f1 = new MonFil();
    MonFil2 f2 = new MonFil2();
    f1.start(); //Pour lancer le multithreading
    f2.start(); //Pour lancer le multithreading
}