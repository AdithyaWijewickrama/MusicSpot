package application;

import java.io.IOException;

import account.Account;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class LoginController {
	@FXML
	public TextField usernameTxt;
	@FXML
	public PasswordField passwordTxt;
	@FXML
	public Label required;

	@FXML
	public void login(ActionEvent e) throws IOException {
		String un = usernameTxt.getText();
		String psw = passwordTxt.getText();
		if (!un.equals("") && !psw.equals("")) {
			if (Account.hasAccount(un, psw)) {
				Account.user=new Account(un,psw);
				root = FXMLLoader.load(getClass().getResource("MainWin.fxml"));
				stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
				scene = new Scene(root);
				stage.setScene(scene);
				stage.centerOnScreen();
				stage.show();
			} else {
				Alert a = new Alert(AlertType.ERROR);
				a.setContentText("Username or password is incorrect");
				a.show();
			}
		} else {
			required.setText("Username & password is required");
		}
	}

	@FXML
	public void gotopassword() {
		passwordTxt.requestFocus();
	}

	@FXML
	public void resetWarnings() {
		required.setText("");
	}

	public Stage stage;
	public Parent root;
	public Scene scene;
	@FXML
	public void switchToSignIn(ActionEvent e) throws IOException {
		root = FXMLLoader.load(getClass().getResource("/SignIn.fxml"));
		stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
		scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
	}
	
	public void swtchMusicSpot(ActionEvent e) throws IOException {
		root = FXMLLoader.load(getClass().getResource("SignIn.fxml"));
		stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
		scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
	}
	
}
