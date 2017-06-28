import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import org.controlsfx.control.Notifications;


public class ControlBarController {

    @FXML
    private TextField sizeField;
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

    private boolean startButtonClicked = false;

    private StateSaverModel saver;

    MergeVisualizerModel model;

    BooleanProperty visEnd = new SimpleBooleanProperty(false);

    @FXML
    void initialize() {
        startButton.setGraphic(new ImageView(new Image(getClass().getResourceAsStream("/play3.png"))));
        pauseButton.setGraphic(new ImageView(new Image(getClass().getResourceAsStream("/pause2.png"))));
        prevButton .setGraphic(new ImageView(new Image(getClass().getResourceAsStream("/arrow-left2.png"))));
        nextButton .setGraphic(new ImageView(new Image(getClass().getResourceAsStream("/arrow-right2.png"))));
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

    private static void setEnabled(Node node, boolean state) {
        node.setDisable(!state);
    }

    private void initializeEventHandlers() {
        tg.selectedToggleProperty().addListener((observable, oldValue, newValue) -> {
            if(getSelectedMode().equals("Custom")) {
                if(startButtonClicked) {
                    setEnabled(customTextArray, false);
                } else {
                    setEnabled(customTextArray, true);
                }
                setEnabled(sizeField, false);
                setEnabled(genButton, false);
            } else {
                setEnabled(sizeField,       true);
                setEnabled(customTextArray, false);
            }
        });
    }

    void initializeControlPanelHandlers(IMediator mediator) {

        startButton.setOnAction(e -> {
            if(startButtonClicked)
                model.continueProcess();
            else {
                model = new MergeVisualizerModel(mediator);
                startButtonClicked = true;
                model.setSortStates(saver);
                visEnd.bind(model.getVisualizationOverProperty());
                model.start();
            }
            setEnabled(startButton, false);
            setEnabled(pauseButton, true);
            setEnabled(prevButton,  false);
            setEnabled(nextButton,  false);
            setEnabled(resetButton, true);
        });

        pauseButton.setOnAction(e -> {
            model.pause();
            setEnabled(startButton, true);
            setEnabled(pauseButton, false);
            setEnabled(prevButton,  true);
            setEnabled(nextButton,  true);
        });

        // TODO: 26.06.2017 dis shet
        prevButton.setOnAction(e -> {
            model.previousStep();
            setEnabled(startButton, true);
            setEnabled(nextButton,  true);
        });

        nextButton.setOnAction(e -> {
            model.nextStep();
            setEnabled(prevButton,  true);
            setEnabled(resetButton, true);
        });

        resetButton.setOnAction(e -> {
            if (model.isAlive()) {
                model.reset();
            } else mediator.resetCalled();
            startButtonClicked = false;
            setEnabled(startButton, true);
            setEnabled(pauseButton, false);
            setEnabled(prevButton,  false);
            setEnabled(nextButton,  true);
            setEnabled(resetButton, false);
        });

        customTextArray.textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.matches("\\d{1,3}(\\s\\d{1,3})*\\s?")) {
                setEnabled(genButton, true);
            } else {
                Notifications.create().title("Parse error").text("Field must be filled with numbers from 0 to 999 separated with spaces").showError();
                setEnabled(genButton, false);
            }
        });

        visEnd.addListener((observable, oldValue, newValue) -> {
            setEnabled(startButton, true);
            setEnabled(pauseButton, false);
        });
    }

    void setArraySizeFieldHandler(Command command) {
        sizeField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.length() < 9 && newValue.matches("\\d+$")) {
                Integer parsedArraySize = Integer.parseInt(newValue);
                if (parsedArraySize >= 0 && parsedArraySize <= 250) {
                    command.execute();
                    genButton.setDisable(false);
                } else {
                    genButton.setDisable(true);
                    Notifications.create().title("Length error").text("Array size must be 1-250").showError();
                }
            } else {
                genButton.setDisable(true);
                if (newValue.length() > 0)
                    Notifications.create().title("Input data error").text("Field must be filled with number from 1 to 250").showError();
            }
        });
    }

    void setGenButtonHandler(IntArray array) {
        genButton.setOnAction(e -> {

            SortPerformer sorter = new MergeSortPerformerModel();
            int[] arr;
            if (getSelectedMode().equals("Custom")) {
                arr = getCustomArray();
            } else {
                arr = new IntArrayGenerator().generate(getParsedArraySize(), getSelectedMode());
            }
            array.copy(arr);
            sorter.setArray(arr);
            sorter.performSort();
            saver = sorter.getSaver();
            setEnabled(startButton, true);
            setEnabled(nextButton,  true);
        });
    }

    public int[] getCustomArray() {
        if (customTextArray.getText().matches("\\d{1,3}(\\s\\d{1,3})*\\s?")) {
            String[] textArr = customTextArray.getText().split(" ");
            int[] arr = new int[textArr.length];
            for (int i = 0; i < textArr.length; i++) {
                arr[i] = Integer.parseInt(textArr[i]);
            }
            return arr;
        }
        return null;
    }

    public String getSelectedMode() {
        return ((RadioButton)tg.getSelectedToggle()).getText();
    }

    public Integer getParsedArraySize() {
        if (sizeField.getText().length() < 9 && sizeField.getText().matches("\\d+$")) {
            return Integer.parseInt(sizeField.getText());
        } else {
            return -1;
        }
    }
}
