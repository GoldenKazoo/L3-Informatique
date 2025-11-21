import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import javax.imageio.ImageIO;
import javax.swing.*;

public class LoadAndShow extends JPanel {
    BufferedImage image;
    Dimension size = new Dimension();

    public LoadAndShow(BufferedImage image) {
        this.image = image;
        size.setSize(image.getWidth(), image.getHeight());
    }

    
    protected void paintComponent(Graphics g) {
        // Center image in this component.
        int x = (getWidth() - size.width)/2;
        int y = (getHeight() - size.height)/2;
        g.drawImage(image, x, y, this);
    }

    public Dimension getPreferredSize() { return size; }

    public static void show(String path){
        try{
        BufferedImage image = ImageIO.read(new File(path));
        LoadAndShow test = new LoadAndShow(image);
        JFrame f = new JFrame();
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.add(new JScrollPane(test));
        f.setSize(image.getWidth(),image.getHeight());
        f.setLocation(10,10);
        f.setVisible(true);
        }
        catch(Exception e){}
    }
}