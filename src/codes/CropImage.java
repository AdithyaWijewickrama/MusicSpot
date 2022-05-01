package codes;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.geom.Ellipse2D;
import java.awt.image.BufferedImage;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.ImageIcon;

public class CropImage {

    Image src;
    Rectangle rect;

    public CropImage(Image img, Rectangle rect) {
        this.src = img;
        this.rect = rect;
    }

    public BufferedImage crop(){
        BufferedImage dest = new BufferedImage(rect.width, rect.height, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = (Graphics2D) dest.getGraphics();
        g.drawImage(src, 0, 0, (int) rect.getWidth(), (int) rect.getHeight(), (int) rect.getX(), (int) rect.getY(), (int) (rect.getX() + rect.getWidth()), (int) (rect.getY() + rect.getHeight()), null);
        g.dispose();
        return dest;
    }

    public BufferedImage cropCircle() {
        BufferedImage dest = new BufferedImage(rect.width, rect.height, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = (Graphics2D) dest.getGraphics();
        g.setClip(new Ellipse2D.Float(0, 0, rect.width, rect.height));
        g.drawImage(src, 0, 0, (int) rect.getWidth(), (int) rect.getHeight(), (int) rect.getX(), (int) rect.getY(), (int) (rect.getX() + rect.getWidth()), (int) (rect.getY() + rect.getHeight()), null);
        g.dispose();
        return dest;
    }

    public BufferedImage crop(Image src, Rectangle r){
        BufferedImage dest = new BufferedImage(r.width, r.height, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = (Graphics2D) dest.getGraphics();
        g.drawImage(src, 0, 0, (int) rect.getWidth(), (int) rect.getHeight(), (int) rect.getX(), (int) rect.getY(), (int) (rect.getX() + rect.getWidth()), (int) (rect.getY() + rect.getHeight()), null);
        g.dispose();
        return dest;
    }

    public BufferedImage cropCircle(Image src, Rectangle r) throws Exception {
        BufferedImage dest = new CropImage(src, r).crop();
        Graphics2D g = (Graphics2D) dest.getGraphics();
        g.setClip(new Ellipse2D.Float(0,0, r.width, r.height));
        g.dispose();
        return dest;
    }

    public static void main(String[] args) {
        String s = "src\\Images\\Background-3.jpg";
        BufferedImage im;
        try {
            im = new CropImage(new ImageIcon(s).getImage(), new Rectangle(120, 0, 300, 300)).cropCircle();
        } catch (Exception ex) {
            Logger.getLogger(CropImage.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
