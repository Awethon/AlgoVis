import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
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

    private ToggleGroup tg;
    private TextField textArray;

    @FXML
    void initialize() {
        startButton.setGraphic(new ImageView(new Image(getClass().getResourceAsStream("/play3.png"))));
        pauseButton.setGraphic(new ImageView(new Image(getClass().getResourceAsStream("/pause2.png"))));
        nextButton.setGraphic(new ImageView(new Image(getClass().getResourceAsStream("/arrow-right2.png"))));
        resetButton.setGraphic(new ImageView(new Image(getClass().getResourceAsStream("/rotate.png"))));

        RadioButton[] rbs = new RadioButton[4];
        tg = new ToggleGroup();
        rbs[0] = new RadioButton("Random");
        rbs[1] = new RadioButton("Almost sorted");
        rbs[2] = new RadioButton("Reversed");
        rbs[3] = new RadioButton("Custom");
        tg.getToggles().addAll(rbs);
        rbs[0].setSelected(true);
        radioVBox.getChildren().addAll(rbs);
        textArray = new TextField();
        radioVBox.getChildren().add(textArray);
        textArray.setDisable(true);
        tg.selectedToggleProperty().addListener((observable, oldValue, newValue) -> {
            if(((RadioButton)newValue).getText().equals("Custom")) {
                textArray.setDisable(false);
            } else {
                textArray.setDisable(true);
            }
        });
    }

    public TextField getTextField() {
        return tf;
    }

    public void genButtonSetHandler(EventHandler<ActionEvent> value) {
        genButton.setOnAction(value);
    }

    public Toggle getSelectedToggle(){
        return tg.getSelectedToggle();
    }

    public TextField getTextArray() {
        return textArray;
    }

    public Button getGenButton() {
        return genButton;
    }
}
