package music;

import java.util.Date;
import java.util.List;

import javax.swing.ImageIcon;

import database.DB;
import database.Sql;

public class SongProperties {

    public int id;
    public String name;
    public int artist;
    public int likes;
    public int dislikes;
    public int views;
    public Date date;
    public ImageIcon image;
    public long listen_time;

    public SongProperties(int id) {
        this.id = id;
        List<Object> data = Sql.getRow("SELECT i.name,i.artist,i.thumb,i.date_added,j.* FROM songs i INNER JOIN song_stats j ON i.id = j.id WHERE i.id='" + id + "'", DB.CONN);
        if (data != null) {
            name = (String) data.get(0);
            artist = (int) data.get(1);
            if (data.get(2) != null) {
                image = new ImageIcon((byte[]) data.get(2));
            }
            date = new Date((long) data.get(3));
            likes = (int) data.get(5);
            dislikes = (int) data.get(6);
            views = (int) data.get(7);
            listen_time = (int) data.get(8);
        }
    }

    public Object[] getData() {
        return new Object[]{
            name,
            artist,
            image,
            likes,
            dislikes,
            views,
            date,
            listen_time
        };
    }
}
