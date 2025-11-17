package Philo;

import java.util.Random;

public class Philo implements Runnable
{
    int             id;
    private Fork    fork_l;
    private Fork    fork_r;

    public Philo (int id, Fork gauche, Fork droite)
    {
        this.id = id;
        this.fork_l = gauche;
        this.fork_r = droite;
    }

    public void think()
    {
        Random r = new Random();
        try {
            System.out.println(this.id + " pense");
            Thread.sleep(r.nextInt(500));
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public void sleep()
    {
        Random r = new Random();
        try {
            System.out.println(this.id + " dort");
            Thread.sleep(r.nextInt(500));
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public void eat()
    {
        Random r = new Random();
        try {
            System.out.println(this.id + " mange");
            Thread.sleep(r.nextInt(500));
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        this.fork_l.poser(id);
        this.fork_r.poser(id);
    }

    public void grand_order()
    {
        if (this.id % 2 == 0)
        {
            this.fork_l.prendre(this.id);
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            };
            this.fork_r.prendre(this.id); 
        }
        else
        {
            this.fork_r.prendre(this.id);
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            };
            this.fork_l.prendre(this.id);
        }
    }
    public void run()
    {
        while (true)
        {
            think();
            System.out.println(this.id + " veut manger");
            grand_order();;
            eat();
            sleep();
        }

    }
}
