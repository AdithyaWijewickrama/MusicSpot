package account;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.ImageIcon;

import codes.Commons;
import database.DB;
import database.Sql;

public class Account {

    public String userName;
    public String psw;
    public String email;
    public boolean auth;
    public int id;
    public ImageIcon image;
    public Date date_created;

    public Account(String userName, String psw) {
        List<Object> data = Sql.getRow("SELECT * FROM users WHERE username='" + userName + "'", DB.CONN);
        this.userName = userName;
        this.psw = psw;
        auth = data != null;
        if (auth) {
            image = data.get(3) != null ? new ImageIcon((byte[]) data.get(3)) : null;
            email = (String) data.get(2);
            id = (int) data.get(4);
            date_created = (Date) data.get(5);
        }
    }

    public Account(int id) {
        this.id = id;
        List<Object> data = Sql.getRow("SELECT * FROM users WHERE id='" + id + "'", DB.CONN);
        if (data != null) {
            userName = (String) data.get(0);
            psw = (String) data.get(1);
            email = (String) data.get(2);
            if(data.get(3)!=null)
                image = new ImageIcon((byte[]) data.get(3));
        }
    }
    
    public static boolean hasAccount(String un,String psw) {
    	return Sql.getValue("SELECT username FROM users WHERE password='"+psw+"' AND username='"+un+"'", DB.CONN)!=null;
    }

    public void updateAcc() {
        try {
            Sql.Execute("INSERT OR REPLACE INTO users (user_name,email,password) VALUES('" + userName + "','" + email + "','" + psw + "')", DB.CONN);
        } catch (SQLException ex) {
            Commons.showErr(ex.toString());
            Logger.getLogger(Account.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void deleteAcc(String msg) {
        try {
            Sql.Execute("DELETE FROM users WHERE id='" + id + "'", DB.CONN);
            Sql.Execute("DELETE FROM favorite_songs WHERE user_id='" + id + "'", DB.CONN);
            Sql.Execute("DELETE FROM liked_disliked_songs WHERE user_id='" + id + "'", DB.CONN);
            Sql.Execute("DELETE FROM following_artists WHERE id='" + id + "'", DB.CONN);
            Sql.Execute("DELETE FROM users WHERE id='" + id + "'", DB.CONN);
        } catch (SQLException ex) {
            Logger.getLogger(Account.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public static final Account dummy = new Account(2);
    public static Account user=dummy;
    public static void main(String[] args) {
		System.out.println(Sql.getValue("SELECT username FROM users WHERE password='1111'", DB.CONN));
	}
}
