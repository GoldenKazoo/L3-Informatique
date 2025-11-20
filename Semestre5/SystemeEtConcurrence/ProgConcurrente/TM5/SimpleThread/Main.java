package SimpleThread;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingDeque;
import java.util.concurrent.BlockingQueue;

import Blocking_queu_Home_made.Blocking_queu;

public class Main
{
    public static void main(String[] args)
    {
        BlockingQueue<Runnable> queu = new ArrayBlockingQueue(10);
        SimpleThread            simple = new SimpleThread(queu);
        Task[] tasks = new Task[2];

        for (int i = 0 ;  i < 2; i++)
        {
            tasks[i] = new Task(i);
        }

        for(int j = 0; j < 2; j++)
        {
            simple.execute(tasks[j]);
        }
    }
}
