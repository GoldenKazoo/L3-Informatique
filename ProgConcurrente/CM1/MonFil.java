class MonFil extends Thread
{
    public void run()
    {
        print("Toto");
        Thread.sleep(100); //Le thread va amimir pendant 100ms
        print("Toto");
        Thread.currentThread().ThreadId();
    }
}

MonFil fil = new MonFil();
fil.start(); //Pour lancer le multithreading
