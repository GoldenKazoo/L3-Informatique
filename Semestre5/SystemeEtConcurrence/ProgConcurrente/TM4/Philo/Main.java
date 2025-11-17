package Philo;

public class Main
{
    public static void main(String[] args)
    {
        Philo[] philosophers = new Philo[5];
        Fork[]  forks = new Fork[5];
        for (int i = 0; i < 5 ; i++)
        {
            forks[i] = new Fork();
        }
        for(int i = 0 ; i < 5 ; i++)
        {
            if (i < 4)
                philosophers[i] = new Philo(i, forks[i],forks[i+1]);
            else
                philosophers[i] = new Philo(i, forks[i],forks[0]);
            new Thread(philosophers[i]).start();
        }
    }
}
