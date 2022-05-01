package application;

import java.awt.event.ActionEvent;
import java.io.IOException;

import database.DB;
import database.Sql;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Paint;
import javafx.stage.Stage;

public class SignInController {
	@FXML
	public TextField usernameTxt;
	@FXML
	public TextField passwordTxt;
	@FXML
	public TextField emailTxt;
	@FXML
	public Label usernameValidness;
	@FXML
	public Label passwordValidness;
	@FXML
	public Label emailValidness;
	@FXML
	public ProgressBar passwordProg;
	@FXML
	public Button signInBtn;
	public Stage stage;
	public Parent root;
	public Scene scene;
	@FXML
	public void signIn(ActionEvent e) throws IOException {
		root = FXMLLoader.load(getClass().getResource("/MusicSpot.fxml"));
		stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
		scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
	}

	@FXML
	public void usernameValidness() {
		String like = Sql.getValueS("SELECT username FROM users WHERE username='" + usernameTxt.getText()+"'", DB.CONN);
		if (like != null) {
			usernameValidness.setText("Username already in use :(");
			usernameValidness.setTextFill(Paint.valueOf("red"));
			signInBtn.setDisable(true);
		} else {
			usernameValidness.setText("Username is ok :)");
			usernameValidness.setTextFill(Paint.valueOf("green"));
			signInBtn.setDisable(false);
		}
	}

	@FXML
	public void emailValidness() {
		String email = Sql.getValueS("SELECT email FROM users WHERE email='" + emailTxt.getText()+"'", DB.CONN);
		if (email != null) {
			emailValidness.setText("Email is not yours :(");
			emailValidness.setTextFill(Paint.valueOf("red"));
			signInBtn.setDisable(true);
		} else {
			emailValidness.setText("Email is ok :)");
			emailValidness.setTextFill(Paint.valueOf("green"));
			signInBtn.setDisable(false);
		}
	}

	@FXML
	public void passwordProgress(KeyEvent e) {
		String psw=passwordTxt.getText();
		String psw1 = Sql.getValueS("SELECT password FROM users WHERE password='" + psw+"'", DB.CONN);
		if (psw1 != null) {
			passwordValidness.setText("Password already in use :(");
			passwordValidness.setTextFill(Paint.valueOf("red"));
			passwordProg.setProgress(0);
			signInBtn.setDisable(true);
		} else {
			passwordValidness.setText("Password is ok :)");
			passwordValidness.setTextFill(Paint.valueOf("green"));
			signInBtn.setDisable(false);
			//password progress
			int progArray[]=new int[5];
			progArray[0]=psw.length()>=8?1:0;
			for(char c:psw.toCharArray()) {
				progArray[1]=Character.isAlphabetic(c)?1:0;
				progArray[2]=Character.isDigit(c)?1:0;
				progArray[3]=Character.isUpperCase(c)?1:0;
				progArray[4]=!Character.isLetterOrDigit(c)?1:0;
			}
			double prog=0;
			for (int i : progArray) {
				prog+=(i==1?.2:0);
			}
			passwordProg.setProgress(prog);
		}
	}

	@FXML
	public void cancel() {
		
	}
//
//	public static Background getColor(int r, int g, int b) {
//		return new Background(new BackgroundFill(Color.rgb(r, g, b, 1), new CornerRadii(5.0), new Insets(-5.0)));
//	}
}
