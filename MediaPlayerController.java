import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.Timer;
import java.util.TimerTask;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.Slider;
import javafx.scene.layout.AnchorPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;

public class MediaPlayerController implements Initializable {

    @FXML
    private Button nextBtn;

    @FXML
    private AnchorPane pane;

    @FXML
    private Label songLabel;

    @FXML
    private Button playBtn;

    @FXML
    private Button prevBtn;

    @FXML
    private ProgressBar progressBar;

    @FXML
    private Button resetBtn;

    @FXML
    private ComboBox<String> speedComboBox;

    @FXML
    private Slider volumeSlider;

    // local variables

    private File directory;

    private File[] files;

    private ArrayList<File> playlist;

    private int songNumber;

    private int[] speeds = { 25, 50, 75, 100, 125, 150, 175, 200, 225, 250 };

    private Timer timer;

    private TimerTask task;

    private boolean running = false;

    private Media media;

    private MediaPlayer player;

    private boolean Flag;

    // Functions

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {

        playlist = new ArrayList<File>();

        directory = new File("src\\Music");

        files = directory.listFiles();

        if (files != null) {
            for (int i = 0; i < files.length; i++) {
                System.out.println(files[i].getName());
                playlist.add(files[i]);
            }
        }

        media = new Media(playlist.get(songNumber).toURI().toString());

        player = new MediaPlayer(media);

        songLabel.setText(playlist.get(songNumber).getName());

        for (int i = 0; i < speeds.length; i++) {
            speedComboBox.getItems().add(speeds[i] + "%");
        }

        speedComboBox.setOnAction(this::speedChange);

        volumeSlider.valueProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> arg0, Number arg1, Number arg2) {
                // TODO Auto-generated method stub
                player.setVolume(volumeSlider.getValue() / 100);
            }

        });

        progressBar.setStyle("-fx-accent: #08609e");

        

        // TODO Auto-generated method stub
        // throw new UnsupportedOperationException("Unimplemented method 'initialize'");
    }

    // Functions

    @FXML
    void playBtnClicked() {
        // System.out.println("Play Change");
        speedChange(null);
        if (getFlag() == false) {
            beginTimer();
            player.play();
            setFlag(true);
        } else if (getFlag() == true) {
            stopTimer();
            player.pause();
            setFlag(false);
        }
    }

    @FXML
    void nextBtnClicked(ActionEvent event) {

        player.pause();

        if (running == true) {
            stopTimer();
        }

        if (songNumber < playlist.size() - 1) {
            songNumber++;
        } else {
            songNumber = 0;
        }

        media = new Media(playlist.get(songNumber).toURI().toString());
        player = new MediaPlayer(media);
        songLabel.setText(playlist.get(songNumber).getName());
        setFlag(false);
        playBtnClicked();

    }

    @FXML
    void prevBtnClicked(ActionEvent event) {
        player.pause();

        player.stop();
        if (running == true) {
            stopTimer();
        }

        if (songNumber > 0) {
            songNumber--;
        } else {
            songNumber = playlist.size() - 1;
        }

        media = new Media(playlist.get(songNumber).toURI().toString());
        player = new MediaPlayer(media);
        songLabel.setText(playlist.get(songNumber).getName());
        setFlag(false);
        playBtnClicked();
    }

    @FXML
    void resetBtnClicked(ActionEvent event) {
        progressBar.setProgress(0 );
        player.seek(Duration.ZERO);
    }

    @FXML
    void speedChange(ActionEvent event) {
        String selectedSpeed = speedComboBox.getValue();
        if (selectedSpeed != null) {
            double speed = Double.parseDouble(selectedSpeed.replace("%", "")) / 100.0;
            player.setRate(speed);
        }
    }

    public void setFlag(boolean flag) {
        Flag = flag;
    }

    public boolean getFlag() {
        return Flag;
    }

    public void beginTimer() {
        timer = new Timer();
        task = new TimerTask() {
            @Override
            public void run() {
                // TODO Auto-generated method stub
                running = true;
                double getCurrentTime = player.getCurrentTime().toSeconds();
                double getDuration = player.getTotalDuration().toSeconds();
                progressBar.setProgress(getCurrentTime / getDuration);
                
                if (getCurrentTime/getDuration == 1) {
                    stopTimer();
                    nextBtnClicked(null);
            }
            
        }
    };
    timer.scheduleAtFixedRate(task, 0, 1000);
    }

    public void stopTimer() {
        running  = false;
        timer.cancel();
        timer.purge();
    }

}
