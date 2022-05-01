package application;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;

public class MainWinController implements Initializable {
	@FXML
	public Label acclabel;
	@FXML
	public TextField searchTxt;
	@FXML
	public Button homeBtn, libraryBtn, playlistBtn, studioBtn, artistsBtn;
	@FXML
	public ScrollPane mainPane;
	@FXML
	public VBox sidePane;
	@FXML
	public BorderPane mainBorderPane; 
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		try {
			Parent root=FXMLLoader.load(getClass().getResource("Player.fxml"));
			mainBorderPane.setBottom((Node)root);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@FXML
	public void gotoHome() {
		
	}
	
	@FXML
	public void gotoLibrary() {
		
	}
	@FXML
	public void createPlayList() {
		
	}
	@FXML
	public void gotoArtists() {
		
	}
	@FXML
	public void gotoStudio() {
		
	}
	@FXML
	public void gotoFollowingArtists() {
		
	}
	@FXML
	public void gotoAccount() {
		
	}
	@FXML
	public void search() {
		
	}
	public void selectBtn(Button b) {
		
	}
	
}
