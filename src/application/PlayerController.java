package application;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Timer;
import java.util.TimerTask;

import account.Account;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;
import music.Song;

public class PlayerController implements Initializable {
	@FXML
	public Button playBtn, prevBtn, nextBtn, favoriteBtn, likeBtn, dislikeBtn, soundBtn;
	@FXML
	public Slider soundAdj, songLine;
	@FXML
	public Label songNameLabel, artistLabel;
	@FXML
	public Label timeLabel1;
	@FXML
	public Label timeLabel2;
	@FXML
	TextField test, test1;
	public Media media;
	public MediaPlayer mediaPlayer;
	public List<Song> songList = new ArrayList<>();
	public int songNumber = 0;
	public Song song;
	public boolean playing = false;
	public Timer timer;
	public TimerTask updateDuration;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {

		soundAdj.valueProperty().addListener(new ChangeListener<Number>() {

			@Override
			public void changed(ObservableValue<? extends Number> arg0, Number arg1, Number arg2) {
				if (mediaPlayer != null) {
					mediaPlayer.setVolume(soundAdj.getValue() * 0.01);
				}
			}

		});
		songLine.valueProperty().addListener(new ChangeListener<Number>() {

			@Override
			public void changed(ObservableValue<? extends Number> arg0, Number arg1, Number arg2) {
				if (mediaPlayer != null) {
					updateTimer();
				}
			}

		});
	}

	public void addSong(Song s) {
		if (songList.size() == 0) {
			setSong(s);
		}
		songList.add(s);
	}

	public void playSong(Song s) {
		addSong(s);
		songNumber = songList.size() - 1;
		media = new Media(s.file.toURI().toString());
		mediaPlayer = new MediaPlayer(media);
		play();
	}

	@FXML
	public void play() {
		if (playing) {
			mediaPlayer.pause();
			cancelTimer();
		} else {
			playing = true;
			mediaPlayer.play();
			beginTimer();
		}
	}

	@FXML
	public void nextSong() {
		if (songNumber != songList.size() - 1) {
			cancelTimer();
			mediaPlayer.pause();
			songNumber++;
			setSong(songList.get(songNumber));
			play();
		}
	}

	@FXML
	public void prevSong() {
		if (songNumber > 0) {
			cancelTimer();
			mediaPlayer.pause();
			songNumber--;
			setSong(songList.get(songNumber));
			play();
		}
	}

	@FXML
	public void jump(MouseEvent e) {
		mediaPlayer.seek(Duration.seconds(mediaPlayer.getTotalDuration().toSeconds() / 100 * songLine.getValue()));
	}

	@FXML
	public void mute() {
		if (mediaPlayer != null) {
			mediaPlayer.setMute(!mediaPlayer.isMute());
			
		}
	}

	@FXML
	public void like() {
		if (song.getOption(Account.user) == 0)
			song.like(Account.user);
		else
			song.nutral(Account.user);
	}

	@FXML
	public void dislike() {
		if (song.getOption(Account.user) == 0)
			song.dislike(Account.user);
		else
			song.nutral(Account.user);
	}

	@FXML
	public void favorite() {
		song.switchFavorite(Account.user, !song.isFav(Account.user));
	}

	@FXML
	void test() {
		for (int i = 36; i < 40; i++) {
			addSong(new Song(i));
		}
		play();
	}

	@FXML
	void test1() {
		mediaPlayer.seek(Duration
				.seconds((mediaPlayer.getTotalDuration().toSeconds() / 100) * Integer.parseInt(test1.getText())));
	}

	public void setSong(Song s) {
		song = s;
		if (!s.ready) {
			s.ready();
		}
		ImageView likeImg = new ImageView(new Image("images/like.png"));
		ImageView dislikeImg = new ImageView(new Image("/images/dislike.png"));
		ImageView favImg = new ImageView(
				new Image(s.isFav(Account.user) ? "/images/notfavorite.png" : "/images/favorite.png"));
		switch (s.getOption(Account.user)) {

		case -1:
			dislikeImg = new ImageView(new Image("/images/disliked.png"));
			break;
		case 1:
			likeImg = new ImageView(new Image("/images/liked.png"));
			break;
		}
		likeImg.setFitHeight(25);
		dislikeImg.setFitHeight(25);
		favImg.setFitHeight(25);
		dislikeBtn.setGraphic(dislikeImg);
		likeBtn.setGraphic(likeImg);
		favoriteBtn.setGraphic(favImg);
		media = new Media(s.file.toURI().toString());
		mediaPlayer = new MediaPlayer(media);
		mediaPlayer.setVolume(soundAdj.getValue() * .01);
		mediaPlayer.setOnEndOfMedia(() -> {
			nextSong();
		});
		songLine.setValue(0);
		songNameLabel.setText(s.name.replace(".mp3", ""));
		artistLabel.setText(new Account(s.artist).userName);
	}

	String getDurationString() {
		int d = (int) mediaPlayer.getCurrentTime().toSeconds();
		return String.format("%02d:%02d:%02d", d / 3600, (d % 3600) / 60, (d % 3600) % 60);
	}

	String getDurationString2() {
		int d = (int) (mediaPlayer.getTotalDuration().toSeconds() - mediaPlayer.getCurrentTime().toSeconds());
		return String.format("%02d:%02d:%02d", d / 3600, (d % 3600) / 60, (d % 3600) % 60);
	}

	private void updateTimer() {
//		timeLabel1.setText(getDurationString());
//		timeLabel2.setText(getDurationString2());
	}

	void updateLine() {
		songLine.setValue(mediaPlayer.getCurrentTime().toSeconds() / mediaPlayer.getTotalDuration().toSeconds() * 100.);
	}

	public void beginTimer() {
		timer = new Timer();
		updateDuration = new TimerTask() {

			@Override
			public void run() {
				if (songList.size() > 0) {
					if (playing) {
						updateLine();
					}
				}
			}
		};
		timer.scheduleAtFixedRate(updateDuration, 0, 1000);
	}

	public void cancelTimer() {
		playing = false;
		timer.cancel();
	}
}
