package Blocking_queu_Home_made;

import java.util.ArrayList;

public class Blocking_queu 
{
    int                 size;
    ArrayList<Object>   queu = new ArrayList<>();
    

    public Blocking_queu (int size)
    {
        this.size = size;
    }

    public synchronized void get ()
    {

        if (this.queu.size() == 0)
            try {
                wait();
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        this.queu.removeFirst();
    }


    public synchronized void put (Object obj)
    {
        if (this.queu.size() > size)
            try {
                wait();
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        this.queu.add(obj);
        notify();
    }

    int    check_size(int size)
    {
        if (size > this.size || size < 0)
        {
            return (-1);
        }
        return (this.queu.size());
    }
}
