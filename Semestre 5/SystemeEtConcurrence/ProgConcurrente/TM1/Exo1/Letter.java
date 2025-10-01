public class Letter extends Threads
{
    char letter;

    public Letter(char x)
    {
        this.letter = x;
    }
    public void run()
    {
        print(letter);
    }
}