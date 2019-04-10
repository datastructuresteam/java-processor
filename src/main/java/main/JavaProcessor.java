package main;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class JavaProcessor extends Application {

	@Override
	public void start(Stage primaryStage) throws Exception {
		Parent root = FXMLLoader.load(getClass().getResource("/views/main.fxml"));

		Scene scene = new Scene(root);

		primaryStage.setTitle("JavaFX and Maven");
		primaryStage.setScene(scene);
		primaryStage.show();
	}

	public static void main(String[] args) throws IOException {
		launch(args);

//		List<String> fileContent = Files.readAllLines(Paths.get("syntax-test.txt"));
//
//		JavaParser parser = new JavaParser(fileContent);
//
//		List<String> formattedContent = parser.getFormattedContent();
//
//		JavaFormatter formatter = new JavaFormatter(formattedContent);
//
//		formattedContent = formatter.getFormattedContent();
//
//		formattedContent.stream().forEach(System.out::println);

	}

}
