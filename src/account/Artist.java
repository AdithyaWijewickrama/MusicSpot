/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package account;

import java.io.File;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

import codes.Commons;
import database.DB;
import database.Sql;

/**
 *
 * @author Dell
 */
public class Artist extends Account{
    public Artist(int id) {
        super(id);
    }
    public void bocomeArtist(String desc){
        try {
            Sql.Execute("INSERT INTO artists (id,description) VALUES('"+id+"','"+desc+"')", DB.CONN);
        } catch (SQLException ex) {
            Logger.getLogger(Artist.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public ArrayList<Integer> getFollowers(){
        ArrayList<Integer> ar=new ArrayList<>();
        for (Object in : Sql.getColumn("SELECT user_id FROM following_artists WHERE artist_id='"+id+"'", 0, DB.CONN)) {
            ar.add((int)in);
        }
        return ar;
    }
    public boolean isAnArtist(){
        return Sql.getValue("SELECT id FROM artists WHERE id='"+id+"'", DB.CONN)==null;
    }
    public void addSong(String name,File song,File thumb) throws IOException, SQLException{
        PreparedStatement pst = DB.CONN.prepareStatement("INSERT INTO songs (name,artist,date_added,song"+(thumb!=null?",thumb":"")+") VALUES('"+name+"','"+id+"','"+Commons.CurrentDate()+"',?"+(thumb!=null?",?":"")+")");
        pst.setBytes(1, Commons.getBytes(song).toByteArray());
        if(thumb!=null){
            pst.setBytes(2, Commons.getBytes(song).toByteArray());
        }
        pst.execute();
    }
    public static void main(String[] args) {
        Artist a =new Artist(1);
        try {
            a.addSong("Temp", new File("C:\\Users\\Dell\\Documents\\NetBeansProjects\\MusicSpot\\temp.mp3"), null);
        } catch (IOException ex) {
            Logger.getLogger(Artist.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(Artist.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
