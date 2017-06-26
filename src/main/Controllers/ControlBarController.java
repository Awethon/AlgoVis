import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
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
    private Button prevButton;
    @FXML
    private Button nextButton;
    @FXML
    private Button resetButton;
    @FXML
    private VBox radioVBox;
    @FXML
    private Button genButton;

    private ToggleGroup tg;
    private TextField customTextArray;

    @FXML
    void initialize() {
        startButton.setGraphic(new ImageView(new Image(getClass().getResourceAsStream("/play3.png"))));
        pauseButton.setGraphic(new ImageView(new Image(getClass().getResourceAsStream("/pause2.png"))));
        prevButton.setGraphic(new ImageView(new Image(getClass().getResourceAsStream("/arrow-left2.png"))));
        nextButton.setGraphic(new ImageView(new Image(getClass().getResourceAsStream("/arrow-right2.png"))));
        resetButton.setGraphic(new ImageView(new Image(getClass().getResourceAsStream("/rotate.png"))));

        RadioButton[] rbs = new RadioButton[4];
        tg = new ToggleGroup();

        rbs[0] = new RadioButton("Random");
        rbs[1] = new RadioButton("Almost sorted");
        rbs[2] = new RadioButton("Reversed");
        rbs[3] = new RadioButton("Custom");

        rbs[0].setSelected(true);
        tg.getToggles().addAll(rbs);
        radioVBox.getChildren().addAll(rbs);
        customTextArray = new TextField();
        customTextArray.setDisable(true);
        radioVBox.getChildren().add(customTextArray);

        initializeEventHandlers();
    }

    private void initializeEventHandlers() {
        tg.selectedToggleProperty().addListener((observable, oldValue, newValue) -> {
            if(getSelectedMode().equals("Custom")) {
                setEnabled(tf,              false);
                setEnabled(customTextArray, true);
                setEnabled(genButton,       false);
            } else {
                setEnabled(tf,              true);
                setEnabled(customTextArray, false);
                setEnabled(genButton,       true);
            }
        });

        setButtonHandler(genButton, (e) -> {
            //Controller.getViewModel().generateSequence();
            setEnabled(startButton, true);
            setEnabled(nextButton, true);
        });

        setButtonHandler(startButton, (e) -> {
            //Controller.getViewModel().start();
            setEnabled(startButton, false);
            setEnabled(pauseButton, true);
            setEnabled(prevButton, false);
            setEnabled(nextButton, false);
            setEnabled(resetButton, true);
        });

        setButtonHandler(pauseButton, (e) -> {
            //Controller.getViewModel().pause();
            setEnabled(startButton, true);
            setEnabled(pauseButton, false);
            setEnabled(prevButton, true);
            setEnabled(nextButton, true);
        });

        setButtonHandler(nextButton, (e) -> {
            //Controller.getViewModel().nextStep();
            setEnabled(prevButton, true);
            setEnabled(resetButton, true);
        });

        setButtonHandler(resetButton, (e) -> {
            //Controller.getViewModel().reset();
            setEnabled(startButton, true);
            setEnabled(pauseButton, false);
            setEnabled(prevButton, false);
            setEnabled(nextButton, true);
            setEnabled(resetButton, false);
        });
    }

    public TextField getTextField() {
        return tf;
    }

    public void genButtonSetHandler(EventHandler<ActionEvent> value) {
        genButton.setOnAction(value);
    }

    public void setButtonHandler(Button button, EventHandler<ActionEvent> value){
        button.setOnAction(value);
    }

    public Toggle getSelectedToggle(){
        return tg.getSelectedToggle();
    }

    public TextField getCustomTextArray() {
        return customTextArray;
    }

    public Button getGenButton() {
        return genButton;
    }

    public String getSelectedMode() {
        return ((RadioButton)getSelectedToggle()).getText();
    }

    public void setEnabled(Node node, boolean state) {
        node.setDisable(!state);
    }
}
