import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import javax.imageio.ImageIO;
import javax.swing.*;

public class MyImage extends JPanel {
    BufferedImage image;
    Dimension size = new Dimension();

    public MyImage(String path) {
        try{
            this.image = ImageIO.read(new File(path));
            size.setSize(image.getWidth(), image.getHeight());
        }
        catch(Exception e)
        {
            System.out.println("Error creating image with path "+path);
            System.out.println(e);
        }            
    }
    
    /**
     * Drawing an image can allow for more
     * flexibility in processing/editing.
     */
    protected void paintComponent(Graphics g) {
        // Center image in this component.
        int x = (getWidth() - size.width)/2;
        int y = (getHeight() - size.height)/2;
        g.drawImage(image, x, y, this);
    }

    public void display(){
        try{
            JFrame f = new JFrame();
            f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            f.add(new JScrollPane(this));
            f.setSize(image.getWidth(),image.getHeight());
            f.setLocation(10,10);
            f.setVisible(true);
        }
        catch(Exception e){}
    }
    
    public void paintOver(String otherimage, int decalx, int decaly)
    {
        BufferedImage newImage=null;
        
        try{
            newImage = ImageIO.read(new File(otherimage));
        }
        catch(Exception e)
        {
            System.out.println("Error creating image with path "+otherimage);
            System.out.println(e);
        }
        
        int sizex = newImage.getWidth();
        int sizey = newImage.getHeight();
        
        int pixel;
        for(int x=0; x<sizex; x++)
            for(int y=0; y<sizey; y++)
            {
                pixel = newImage.getRGB(x,y);
                int alpha_new = (pixel >>24) & 0xFF;
                if(alpha_new>0)
                {
                    if( ((x+decalx) < image.getWidth()) &&
                        ((y+decaly) < image.getHeight()) )
                    {
                        double alpha = alpha_new/255.0;
                        int pixel_orig = image.getRGB(x+decalx,y+decaly);
                        int red_orig = ((pixel_orig >>16) & 0xFF);
                        int green_orig = ((pixel_orig >>8 ) & 0xFF);
                        int blue_orig = (pixel_orig & 0xFF);
                        int red_new = ((pixel >>16 ) & 0xFF);
                        int green_new = ((pixel >>8 ) & 0xFF);
                        int blue_new = (pixel & 0xFF);
                        int final_red = (int)(red_new*alpha + red_orig*(1.0-alpha));
                        int final_green = (int)(green_new*alpha + green_orig*(1.0-alpha));
                        int final_blue = (int)(blue_new*alpha + blue_orig*(1.0-alpha));
                        int pixel_final = final_red*65536+final_green*256+final_blue;
                        image.setRGB(x+decalx,y+decaly,pixel_final);
                    }
                }
            }
    }
}