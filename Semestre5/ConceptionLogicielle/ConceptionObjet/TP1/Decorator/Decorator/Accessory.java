public abstract class Accessory implements Picture
{
    private String path;
    Picture picture;
    int posX;
    int posY;

    public Accessory(Picture picture, String path, int posX, int posY)
    {
        this.picture = picture;
        this.path = path;
        this.posX = posX;
        this.posY = posY;
    }

    public MyImage buildImage()
    {
        MyImage base = picture.buildImage();
        base.paintOver(path, x, y);
        return base;
    }
}