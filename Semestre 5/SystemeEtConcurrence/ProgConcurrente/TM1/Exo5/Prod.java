public class Conso extends Thread
{
    FlagNumber flag;
    this.id;

    public Conso (String name, FlagNumber flag)
    {
        this.id = name;
        this.flag = flag;
    }

    public void run()
    {
        println("Im the produceur");
        if (flag.flag = false)
        {
            flag.flag = true;
            println("The flag is now set to true");
            flag.x++;
        }
    }
}