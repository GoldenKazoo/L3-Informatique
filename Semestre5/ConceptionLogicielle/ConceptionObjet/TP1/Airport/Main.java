
public class Main
{
    public static void main(String args[])
    {
        Plane a1 = new Plane("Avion 1");
        Plane a2 = new Plane("Avion 2");
        Plane a3 = new Plane("Avion 3");
        
        a1.start();
        a2.start();
        a3.start();
        
        
        try{
            a1.join();
            a2.join();
            a3.join();
        }
        catch(Exception e){}
    


        if(Airport.hasAccident()){
            System.out.println("CRASH!"); 
            LoadAndShow.show("./img/rate.jpg");
        }
        else {
            System.out.println("TOP! EXO RÉUSSI!"); 
            LoadAndShow.show("img/bravo.jpg");
        }
    }
}
