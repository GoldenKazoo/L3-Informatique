package Hammam;

public class Hammam {
    int size = 4;
    int places_libre = 4;;
    boolean plein;

    public void check_place() {
        if (places_libre <= 0)
            this.plein = true;
        else
            this.plein = false;
    }

    public void prendre_bain(int id)
    {
        System.out.println(id + " prends un bain");
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        System.out.println(id + " sors du bain");
    }

    public void enter(int id) {
        synchronized (this)
        {
        while (places_libre == 0)
        {
            System.out.println("Le hammam est plein " + id + ", priere de patienter...");
            try {
                wait();
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }
        this.places_libre--;
        this.prendre_bain(id);
        synchronized(this)
        {
            notify();
        }
        this.places_libre++;
    }
}
