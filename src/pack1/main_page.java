package pack1;
	
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;

public class main_page extends Application {
	private double xOffset = 0;
    private double yOffset = 0;
	@Override
	public void start(Stage stage) throws Exception {
	      Parent root = FXMLLoader.load(getClass().getResource("main3.fxml"));
	       //FXMLLoader loader = new FXMLLoader(main_page.class.getResource("Main_fxml.fxml"));
	       //Parent root =  loader.load();
	    
	        Scene scene = new Scene(root);
	    
	        stage.setTitle("Main Page");
	        stage.setScene(scene);
	        stage.initStyle(StageStyle.TRANSPARENT);

            // Grab your root here
            root.setOnMousePressed((MouseEvent event) -> {
                xOffset = event.getSceneX();
                yOffset = event.getSceneY();
            });

            // Move around here
            root.setOnMouseDragged((MouseEvent event) -> {
                stage.setX(event.getScreenX() - xOffset);
                stage.setY(event.getScreenY() - yOffset);
            });
	        stage.show();
	    }
		public static void main(String[] args) {
			launch(args);
		}
}
