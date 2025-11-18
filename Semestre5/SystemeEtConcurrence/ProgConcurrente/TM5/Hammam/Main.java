package Hammam;

public class Main
{
    public static void main(String[] args)
    {
        Hammam hammam = new Hammam();

        Client[] clientele = new Client[10];
        for (int i = 0; i < 10; i++)
        {
            clientele[i] = new Client(i, hammam);
            new Thread(clientele[i]).start();
        }
    }
}
