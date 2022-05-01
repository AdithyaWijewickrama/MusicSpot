package codes;

import java.awt.Image;

import javax.swing.ImageIcon;

public class AppConfig {

    public static final String APPNAME = "Music Spot";
    public static final ImageIcon APPICON = new ImageIcon("src\\Images\\Logo.png");
    
    public static final ImageIcon SECUREICON = new ImageIcon("src\\Images\\Logo-2.png");
    public static final Image APPICON_32 = APPICON.getImage().getScaledInstance(32, 32, Image.SCALE_SMOOTH);
    public static final Image APPICON_50 = APPICON.getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH);
  
    public static void main(String[] args) {
    }
}
