package codes;

import java.awt.Color;
import java.awt.Toolkit;
import java.awt.datatransfer.StringSelection;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Time;
import java.util.Calendar;
import java.util.Date;

import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

public class Commons {

    public static Date today = Calendar.getInstance().getTime();

    public static final Color APPCOLOR = new Color(12, 91, 121);
    public static final ImageIcon LIKE = new ImageIcon("src\\images\\like.png");
    public static final ImageIcon LIKED = new ImageIcon("src\\images\\liked.png");
    public static final ImageIcon DISLIKE = new ImageIcon("src\\images\\dislike.png");
    public static final ImageIcon DISLIKED = new ImageIcon("src\\images\\disliked.png");
    public static final ImageIcon FAVORITE = new ImageIcon("src\\images");
    public static final ImageIcon NOT_FAVORITE = new ImageIcon("src\\images");
    public static final ImageIcon PLAY = new ImageIcon("src\\images\\play.png");
    public static final ImageIcon SEARCH = new ImageIcon("src\\images\\search.png");
    public static final ImageIcon STOP = new ImageIcon("src\\images\\pause.png");
    public static final ImageIcon ADD = new ImageIcon("src\\images");
    public static final ImageIcon USER = new ImageIcon("src\\images\\user.png");
    public static final ImageIcon NEXT = new ImageIcon("src\\images\\next.png");
    public static final ImageIcon PREVIOUS = new ImageIcon("src\\images\\previous.png");

    public static void CopytoClipboard(String text) {
        java.awt.datatransfer.Clipboard clip = Toolkit.getDefaultToolkit().getSystemClipboard();
        StringSelection sel = new StringSelection(text);
        clip.setContents(sel, sel);
    }

    public static int showConfMsg(String e) {
        return JOptionPane.showConfirmDialog(null, e);
    }

    public static void showMsg(Exception e) {
        JOptionPane.showMessageDialog(null, e);
    }

    public static void showMsg(String e) {
        JOptionPane.showMessageDialog(null, e, AppConfig.APPNAME, JOptionPane.INFORMATION_MESSAGE);
    }

    public static void showErr(String e) {
        JOptionPane.showMessageDialog(null, e, AppConfig.APPNAME, JOptionPane.ERROR_MESSAGE);
    }

    public static void showWar(String e) {
        JOptionPane.showMessageDialog(null, e, AppConfig.APPNAME, JOptionPane.WARNING_MESSAGE);
    }

    public static String CurrentDate() {
        return String.format("%04d-%02d-%02d", today.getYear()+1900, today.getMonth() + 1, today.getDate());
    }

    public static Date currentDate() {
        return today;
    }

    public static String CurrentTime() {
        return String.format("%02d:%02d:%02d", today.getHours(), today.getMinutes(), today.getSeconds());
    }

    public static void openFile(String Path_Name) throws IOException {
        Runtime.getRuntime().exec("rundll32 url.dll,FileProtocolHandler " + Path_Name);
    }

    public static ImageIcon getImage(String path, int Width, int Height) {
        if (path == null) {
            return null;
        }
        return getImage(new ImageIcon(path), Width, Height);
    }

    public static ByteArrayOutputStream getBytes(File f) throws FileNotFoundException, IOException {
        if (f != null) {
            FileInputStream in = new FileInputStream(f);
            ByteArrayOutputStream bout = new ByteArrayOutputStream();
            byte[] buf = new byte[1024];
            for (int r; (r = in.read(buf)) != -1;) {
                bout.write(buf, 0, r);
            }
            return bout;
        } else {
            return null;
        }
    }

    public static ImageIcon getImage(ImageIcon Image, int Width, int Height) {
        if (Image == null) {
            return null;
        }
        if (Width == 0) {
            Width = Image.getIconWidth();
        }
        if (Height == 0) {
            Height = Image.getIconHeight();
        }
        java.awt.Image img = Image.getImage();
        java.awt.Image newimg = img.getScaledInstance(Width, Height, 25);
        ImageIcon i = new ImageIcon(newimg);
        return i;
    }

    public static String showDate(Date d) {
        return String.format("%04d-%02d-%02d", d.getYear(), d.getMonth() + 1, d.getDate());
    }

    public static String showTime(Time d) {
        return String.format("%02d:%02d:%02d", d.getHours(), d.getMinutes() + 1, d.getSeconds());
    }
}
