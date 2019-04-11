package main;

import java.io.IOException;
import java.net.URL;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class JavaProcessor extends Application {

	@Override
	public void start(Stage primaryStage) throws Exception {
		URL resource = getClass().getResource("/views/main.fxml");
		
		Parent root = FXMLLoader.load(resource);

		Scene scene = new Scene(root);

		primaryStage.setTitle("Project #4: Java Processor");
		primaryStage.setScene(scene);
		primaryStage.show();
	}

	public static void main(String[] args) throws IOException {
		launch(args);
	}

}
