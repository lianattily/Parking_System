package application;
	
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;


public class Main extends Application {
	@Override
	public void start(Stage primaryStage) {
		try {
			System.out.println(getClass().getResource("/EnterAs.fxml"));
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/EnterAs.fxml"));
			AnchorPane root = loader.load();
			MainController controller = loader.getController();
			controller.init(primaryStage);

			Scene scene = new Scene(root);

			primaryStage.setScene(scene);
			primaryStage.setTitle("Parking Booking System");
			//primaryStage.setResizable(false);
			primaryStage.show();		
			
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
