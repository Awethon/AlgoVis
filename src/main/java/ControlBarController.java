import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;

public class ControlBarController {
    @FXML
    private TextField tf;
    @FXML
    private Button startButton;
    @FXML
    private Button pauseButton;
    @FXML
    private Button nextButton;
    @FXML
    private Button resetButton;
    @FXML
    private VBox radioVBox;

    @FXML
    void initialize() {
        startButton.setGraphic(new ImageView(new Image(getClass().getResourceAsStream("/play3.png"))));
        pauseButton.setGraphic(new ImageView(new Image(getClass().getResourceAsStream("/pause2.png"))));
        nextButton.setGraphic(new ImageView(new Image(getClass().getResourceAsStream("/arrow-right2.png"))));
        resetButton.setGraphic(new ImageView(new Image(getClass().getResourceAsStream("/cross.png"))));

        RadioButton[] rbs = new RadioButton[3];
        ToggleGroup tg = new ToggleGroup();
        rbs[0] = new RadioButton("Random");
        rbs[1] = new RadioButton("Almost sorted");
        rbs[2] = new RadioButton("Reversed");
        tg.getToggles().addAll(rbs);
        radioVBox.getChildren().addAll(rbs);
    }

    public TextField getTextField() {
        return tf;
    }
}
