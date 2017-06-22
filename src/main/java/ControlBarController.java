import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import org.controlsfx.glyphfont.Glyph;

import java.util.Objects;

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
    private Button genButton;

    @FXML
    void initialize() {
        startButton.setGraphic(new ImageView(new Image(getClass().getResourceAsStream("/play3.png"))));
        pauseButton.setGraphic(new ImageView(new Image(getClass().getResourceAsStream("/pause2.png"))));
        nextButton.setGraphic(new ImageView(new Image(getClass().getResourceAsStream("/arrow-right2.png"))));
        resetButton.setGraphic(new ImageView(new Image(getClass().getResourceAsStream("/rotate.png"))));

        RadioButton[] rbs = new RadioButton[4];
        ToggleGroup tg = new ToggleGroup();
        rbs[0] = new RadioButton("Random");
        rbs[1] = new RadioButton("Almost sorted");
        rbs[2] = new RadioButton("Reversed");
        rbs[3] = new RadioButton("Custom");
        tg.getToggles().addAll(rbs);
        radioVBox.getChildren().addAll(rbs);
        TextField textArray = new TextField();
        radioVBox.getChildren().add(textArray);
        textArray.setDisable(true);

        genButton.setOnAction((e) -> {
            String text = ((RadioButton)(tg.getSelectedToggle())).getText();
            if (text != null) {
                if (text.equals("Random")) {

                } else if (text.equals("Almost sorted")) {

                } else if (text.equals("Reversed")) {

                } else if (text.equals("Custom")) {

                };
            }
        });
    }

    public TextField getTextField() {
        return tf;
    }
}
