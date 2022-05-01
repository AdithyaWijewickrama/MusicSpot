module Music_Spot.application {
	requires javafx.controls;
	requires javafx.fxml;
	requires java.desktop;
	requires java.sql;
	requires javafx.graphics;
	requires javafx.media;
	requires javafx.swing;
	
	opens application to javafx.graphics, javafx.fxml;
}
