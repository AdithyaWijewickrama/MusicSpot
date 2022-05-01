package account;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

import codes.Commons;
import database.DB;
import database.Sql;

public class PlayList {

    public int id;
    public String name;
    public ArrayList<Integer> songs;
    public Date date_created;
    public int created_by;

    public PlayList(int pl_id, ArrayList<Integer> songs, int created_by, String name) {
        this.id = pl_id;
        this.songs = songs;
        this.date_created = Commons.today;
        this.created_by = created_by;
        this.name = name;
    }

    public PlayList(int pl_id) {
        id = pl_id;
        if (Sql.getValue("SELECT id FROM playlists WHERE id='" + id + "'", DB.CONN) != null) {
            Object[] data = Sql.getRow("SELECT * FROM playlists WHERE id='" + id + "'", DB.CONN);
            date_created = new Date((long) data[1]);
            created_by = (int) data[3];
            name = (String) data[4];
            Arrays.asList(data[2] != null ? data[2].toString().split("|") : new int[0]).forEach((o) -> {
                songs.add(Integer.valueOf(o + ""));
            });
        }
    }

    public PlayList() {
        songs = new ArrayList<>();
    }

    public PlayList addSong(int song_id) {
        if (!songs.contains(song_id)) {
            songs.add(id);
        }
        return this;
    }

    public PlayList removeSong(int song_id) {
        songs.remove(id);
        return this;
    }

    public void save() {
        try {
            Sql.Execute("INSERT OR REPLACE INTO playlist VALUES('" + id + "','" + created_by + "','" + getQuery() + "','" + name + "','" + String.format("%04d-%02d-%02d", date_created.getYear(), date_created.getMonth() + 1, date_created.getDate()) + "') WHERE id='" + id + "'", DB.CONN);
        } catch (SQLException ex) {
            Logger.getLogger(PlayList.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
