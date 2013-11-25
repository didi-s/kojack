package de.detekt.kojack.userInterface;

import java.io.IOException;
import java.net.URL;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class KJViewApplication extends Application {

	@Override
	public void start(Stage primaryStage) throws IOException {
		URL    uri = KJViewApplication.class.getResource( "./didi.fxml" );
		Parent p   = FXMLLoader.load( uri );
		primaryStage.setTitle("Kojack");
		primaryStage.setScene(new Scene(p));
		primaryStage.show();
		
	}

	public static void main(String[] args) {
		launch(args);
	}
}
