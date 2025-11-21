public class MyThread extends Thread
{
    char letter;

    public MyThread(char letter)
    {
        this.letter = letter;
    }
    public void run()
    {
        for(int i = 0; i < 10; i++)
        {
            System.out.println(this.letter);
            try
            {
                sleep(10000);
            }
            catch(InterruptedException e)
            {   
                e.printStackTrace();
            }
        }
    }
}
