
public class ControlTower
{
    private static ControlTower instance;
    public boolean runway_is_free;
    
    private ControlTower()
    {   

        runway_is_free=true;
    }
    
    public static ControlTower getInstance()
    {
        if (instance == null)
        {
            instance = new ControlTower();
        }
        return instance;
    }


    public synchronized boolean reserve_runway(String name)
    {
        if(runway_is_free)
        {
            runway_is_free=false;
            System.out.println("Control tower : "+name + " has the runway.");
            Airport.add_plane_to_runway();
            return true;
        }
        else
        {
            return false;
        }
    }
    
    public synchronized void free_runway(String name)
    {
        System.out.println("Control tower : "+name + " freed the runway.");
        Airport.remove_plane_from_runway();
        runway_is_free=true;
    }
}
