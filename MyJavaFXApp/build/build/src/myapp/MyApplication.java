package myapp;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class MyApplication extends Application {

	@Override
	public void start(Stage primaryStage) {
		BorderPane p = new BorderPane();
		Text t = new Text("Hello FX");
		t.setEffect(new DropShadow(2, 3, 3, Color.RED));
		p.setCenter(t);
		primaryStage.setScene(new Scene(p));
		primaryStage.show();
		
	}

	public static void main(String[] args) {
		launch(args);
	}
}