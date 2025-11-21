public class Dog implements Picture
{

    private String i = "img/Dog.jpg";
        
        
    public MyImage buildImage()
    {
        return new MyImage(i);
    }
        
        
}