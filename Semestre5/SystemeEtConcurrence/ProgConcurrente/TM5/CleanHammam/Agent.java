package CleanHammam;

public class Agent implements Runnable
{
    Hammam hammam;

    public Agent (Hammam hammam)
    {
        this.hammam = hammam;
    }

    public void run()
    {
        while(true)
        {
            this.hammam.clean_hammam();
        }
    }
}
