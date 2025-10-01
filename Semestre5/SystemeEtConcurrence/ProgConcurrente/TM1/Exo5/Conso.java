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
        print("Je suis" + this.id + "et la valeur est de :" + flag.x);
        flag.flag = false;
        print("Je suis" + this.id + "et je vais roupiller:" + flag.x);
        sleep(200);
    }
}