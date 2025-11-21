
public class Main
{
   
    public static void main(String args[])
    {
     
        Dog dog = new Dog();
        MyImage i = dog.buildImage();
        i.display();     
 
        i.paintOver("img/Sunglasses.png", 250,46);
        i.display();
 
        
        

        
        
    }
        
}
