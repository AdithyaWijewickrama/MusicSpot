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

public class UserActions {

    public Object[] data;
    public Account acc;

    public UserActions(Account acc) {
        this.acc = acc;
        if (!acc.auth) {
            throw new UnknownError("Unkown user name");
        } else {
            data = Sql.getRow("SELECT * FROM user_acts WHERE id='" + acc.id + "'", DB.CONN);
        }
    }

    public ArrayList<Integer> getCreatedPlayLists() {
        Object[] pl = Sql.getColumn("SELECT id from playlists WHERE created_by='" + acc.id + "'", 0, DB.CONN);
        ArrayList<Integer> p = new ArrayList<>();
        for (Object i : pl) {
            p.add((int) i);
        }
        return p;
    }

    public PlayList getLikedSongs() {
        PlayList pl = new PlayList();
        Arrays.asList(Sql.getColumn("SELECT song_id FROM liked_disliked_songs WHERE user_id='"+acc.id+"' AND like=1", 0, DB.CONN)).forEach((o) -> {
            pl.songs.add(Integer.valueOf(o + ""));
        });
        return pl;
    }

    public ArrayList<Integer> getDislikedSongs() {
        ArrayList<Integer> pl = new ArrayList<>();
        Arrays.asList(Sql.getColumn("SELECT song_id FROM liked_disliked_songs WHERE user_id='"+acc.id+"' AND dislike=1", 0, DB.CONN)).forEach((o) -> {
            pl.add(Integer.valueOf(o + ""));
        });
        return pl;
    }

    public PlayList getFavSongs() {
        PlayList pl = new PlayList();
        Arrays.asList(Sql.getColumn("SELECT song_id FROM favorite_songs WHERE user_id='"+acc.id+"'", 0, DB.CONN)).forEach((o) -> {
            pl.songs.add(Integer.valueOf(o + ""));
        });
        return pl;
    }

    public PlayList getRecents() {
        PlayList pl = new PlayList();
        Arrays.asList(Sql.getColumn("SELECT song_id FROM viewed_songs WHERE user_id='"+acc.id+"' ORDER BY date DECS LIMIT 10", 0, DB.CONN)).forEach((o) -> {
            pl.songs.add(Integer.valueOf(o + ""));
        });
        return pl;
    }

    public ArrayList<Integer> getFollowingArtists() {
        ArrayList<Integer> pl = new ArrayList<>();
        Arrays.asList(Sql.getColumn("SELECT artist_id FROM following_artists WHERE user_id='"+acc.id+"'", 0, DB.CONN)).forEach((o) -> {
            pl.add(Integer.valueOf(o + ""));
        });
        return pl;
    }

    public PlayList getListenedSongs() {
        PlayList pl = new PlayList();
        Arrays.asList(Sql.getColumn("SELECT song_id FROM viewed_songs WHERE user_id='"+acc.id+"'", 0, DB.CONN)).forEach((o) -> {
            pl.addSong(Integer.valueOf(o + ""));
        });
        return pl;
    }

    public void addLiked(int song_id) {
        try {
            Sql.Execute("UPDATE liked_disliked_songs SET option=1 WHERE user_id='" + acc.id + "' AND song_id='"+song_id+"'", DB.CONN);
        } catch (SQLException ex) {
            Logger.getLogger(UserActions.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void addDisliked(int song_id) {
        try {
            Sql.Execute("UPDATE liked_disliked_songs SET option=-1 WHERE user_id='" + acc.id + "' AND song_id='"+song_id+"'", DB.CONN);
        } catch (SQLException ex) {
            Logger.getLogger(UserActions.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public void removeLike(int song_id) {
        try {
            Sql.Execute("UPDATE liked_disliked_songs SET option=0 WHERE user_id='" + acc.id + "' AND song_id='"+song_id+"'", DB.CONN);
        } catch (SQLException ex) {
            Logger.getLogger(UserActions.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void removeDislike(int song_id) {
        try {
            Sql.Execute("UPDATE liked_disliked_songs SET option=0 WHERE user_id='" + acc.id + "' AND song_id='"+song_id+"'", DB.CONN);
        } catch (SQLException ex) {
            Logger.getLogger(UserActions.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void addFav(int song_id) {
        try {
            Sql.Execute("INSERT INTO favorite_songs VALUES('"+song_id+"','" + acc.id + "')", DB.CONN);
        } catch (SQLException ex) {
            Logger.getLogger(UserActions.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void addFollowArtist(int artist_id) {
        try {
            Sql.Execute("INSERT INTO following_artists VALUES('"+acc.id+"','" + artist_id + "')", DB.CONN);
        } catch (SQLException ex) {
            Logger.getLogger(UserActions.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void addListenedSongs(int song_id,Date view_date,int lis_time) {
        try {
            Sql.Execute("INSERT INTO viewed_songs VALUES('"+acc.id+"','" + song_id + "','"+lis_time+"','"+Commons.showDate(view_date)+"')", DB.CONN);
        } catch (SQLException ex) {
            Logger.getLogger(UserActions.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void removeFav(int song_id) {
        try {
            Sql.Execute("DELETE FROM favorite_songs WHERE user_id='"+acc.id+"' AND artist_id='"+song_id+"'", DB.CONN);
        } catch (SQLException ex) {
            Logger.getLogger(UserActions.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void removeFollowArtist(int artist_id) {
        try {
            Sql.Execute("DELETE FROM following_artists WHERE user_id='"+acc.id+"' AND artist_id='"+artist_id+"'", DB.CONN);
        } catch (SQLException ex) {
            Logger.getLogger(UserActions.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
