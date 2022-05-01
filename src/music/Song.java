package music;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

import account.Account;
import database.DB;
import database.Sql;

public class Song extends SongProperties {

	public boolean ready = false;
	public long currentTime = 0;
	public File file = new File("temp.mp3");
	public boolean playing = false;

	public Song(int id) {
		super(id);
	}

	@SuppressWarnings("resource")
	public void ready() {
		try {
			new FileOutputStream(file).write(Sql.getFile("SELECT song FROM songs WHERE id='" + id + "'", DB.CONN));
			ready = true;
		} catch (IOException ex) {
			Logger.getLogger(Song.class.getName()).log(Level.SEVERE, null, ex);
		}
	}

	public int getOption(Account acc) {
		Object o = Sql.getValue(
				"SELECT option FROM liked_disliked_songs WHERE user_id='" + acc.id + "' AND song_id='" + this.id + "'",
				DB.CONN);
		return o != null ? (int) o : -2;
	}

	public boolean isFav(Account acc) {
		Object o = Sql.getValue(
				"SELECT user_id FROM favorite_songs WHERE user_id='" + acc.id + "' AND song_id='" + this.id + "'",
				DB.CONN);
		return o != null;
	}

	public void like(Account acc) {
		try {
			String query = getOption(acc) != -2
					? "UPDATE liked_disliked_songs SET option='1' WHERE user_id='" + acc.id + "' AND song_id='"
							+ this.id + "'"
					: "INSERT INTO liked_disliked_songs (user_id,song_id,option) VALUES('" + acc.id + "','" + this.id
							+ "','1')";
			Sql.Execute(query, DB.CONN);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void dislike(Account acc) {
		try {
			String query = getOption(acc) != -2
					? "UPDATE liked_disliked_songs SET option='-1' WHERE user_id='" + acc.id + "' AND song_id='"
							+ this.id + "'"
					: "INSERT INTO liked_disliked_songs (user_id,song_id,option) VALUES('" + acc.id + "','" + this.id
							+ "','-1')";
			Sql.Execute(query, DB.CONN);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void nutral(Account acc) {
		try {
			Sql.Execute("DELETE FROM liked_disliked_songs WHERE user_id='" + acc.id + "' AND song_id='" + this.id + "'",
					DB.CONN);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void switchFavorite(Account acc, boolean fav) {
		try {
			Sql.Execute(isFav(acc)
					? "DELETE FROM favorite_songs WHERE user_id='" + acc.id + "' AND song_id='" + this.id + "'"
					: "INSERT INTO favorite_songs (user_id,song_id) VALUES('" + acc.id + "','" + this.id + "','-1')",
					DB.CONN);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
	}
}