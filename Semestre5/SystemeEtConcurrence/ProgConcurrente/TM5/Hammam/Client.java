package Hammam;

public class Client implements Runnable
{
    int id;
    Hammam hammam;

    public Client(int id, Hammam hammam)
    {
        this.id = id;
        this.hammam = hammam;
    }
    public void run()
    {
        while (true)
        {
            hammam.enter(this.id);
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }
}