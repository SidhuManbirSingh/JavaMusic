import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * The main application class that extends JavaFX's Application class.
 * This class is responsible for launching the JavaFX application and setting up the initial stage.
 */
public class App extends Application {

    /**
     * The entry point of the JavaFX application.
     *
     * @param args The command line arguments passed to the application.
     */
    public static void main(String[] args) {
        // System.out.println("Hello, World!");
        launch(args);
    }

    /**
     * This method is called automatically by JavaFX when the application is launched.
     * It sets up the initial stage and loads the FXML file for the media player.
     *
     * @param stage The primary stage of the JavaFX application.
     * @throws Exception If an error occurs while loading the FXML file or setting up the stage.
     */
    @Override
    public void start(Stage stage) throws Exception {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("MediaPlayer.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.setTitle("JavaFX Application");
            stage.show();
        }
        catch(Exception e){
            System.out.println(e);
        }
        // TODO Auto-generated method stub
        // throw new UnsupportedOperationException("Unimplemented method 'start'");
    }
}