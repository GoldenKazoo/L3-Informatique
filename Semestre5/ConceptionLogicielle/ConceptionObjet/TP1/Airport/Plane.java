
public class Plane extends Thread
{
    ControlTower c;
    String name;
    
    public Plane(String n)
    {
        c = c.getInstance();
        name=n;

    }

 
    @Override
    public void run()
    {
        
        for(int i =0; (i<3 && (!Airport.hasAccident())); i++)
        {
            try{
                Thread.sleep((int)(Math.random()*600));
            }
            catch(Exception e){}
            takeoff();
            try{
                Thread.sleep((int)(Math.random()*200));
            }
            catch(Exception e){}
            land();
        }
            
    }

    
    public void takeoff()
    {
        while(!c.reserve_runway(name))
        {
            try{
                Thread.sleep((int)(Math.random()*800));
            }
            catch(Exception e){}
        }
        try{
            Thread.sleep((int)(Math.random()*1500));
        }
        catch(Exception e){}
        c.free_runway(name);
    }
    
    public void land()
    {
        
        while(!c.reserve_runway(name))
        {
            try{
                Thread.sleep((int)(Math.random()*500));
            }
            catch(Exception e){}
        }
        try{
            Thread.sleep((int)(Math.random()*700));
        }
        catch(Exception e){}
        c.free_runway(name);
    }
   
}
