
public class Airport
{
    
    public static int planes_on_runway;
    public static boolean accident;
    //TO DO declarer les variables planes_on_runway et accident 

    public static synchronized void add_plane_to_runway()
    {
        planes_on_runway++;
        check_runways();
    }
    
    public static synchronized void remove_plane_from_runway()
    {
        planes_on_runway--;
        check_runways();
    }
    
    public static synchronized void check_runways()
    {
        if(planes_on_runway>1)
        {
            accident=true;
        }
    }
    
    public static synchronized boolean hasAccident()
    {
        return accident;
    }
}
