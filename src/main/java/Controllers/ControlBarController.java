package Controllers;

import Models.*;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.controlsfx.control.Notifications;

import java.io.IOException;


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
    @FXML
    private Button helpButton;

    private ToggleGroup tg;
    private TextField customTextArray;

    private boolean startButtonClicked = false;

    private StateSaverModel saver;

    MergeVisualizerModel model;

    BooleanProperty visEnd = new SimpleBooleanProperty(false);

    Stage helpStage = new Stage();

    Parent root = null;

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

        setEnabled(helpButton, true);

        try {
            root = FXMLLoader.load(getClass().getResource("/helpView.fxml"));
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        helpStage.setTitle("AlgoVis HELP [Merge Sort]");
        helpStage.setScene(new Scene(root, 500, 600));
        helpStage.setMinWidth(600);
        helpStage.setMinHeight(400);
        helpStage.setMaxWidth(800);
        helpStage.setMaxHeight(800);
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
                if (!startButtonClicked) {
                    setEnabled(genButton, true);
                    setEnabled(sizeField,       true);
                }
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
            setEnabled(genButton, false);
            setEnabled(sizeField, false);
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
            if (model.isAtStart()) setEnabled(prevButton, false);
            setEnabled(startButton, true);
            setEnabled(nextButton,  true);
        });

        nextButton.setOnAction(e -> {
            if (!startButtonClicked) {
                model = new MergeVisualizerModel(mediator);
                startButtonClicked = true;
                model.setSortStates(saver);
                visEnd.bind(model.getVisualizationOverProperty());
                model.pause();
                model.start();
            }
            if (model.getCurrentState() + 1 == model.getSize()){
                setEnabled(nextButton, false);
            }
            else {
                setEnabled(nextButton, true);
            }
            model.nextStep();
            setEnabled(prevButton,  true);
            setEnabled(resetButton, true);
            setEnabled(genButton, false);
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
            setEnabled(genButton, true);
            setEnabled(sizeField, true);
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
            setEnabled(startButton, false);
            setEnabled(pauseButton, false);
            setEnabled(nextButton, false);
            setEnabled(prevButton, true);
        });

        helpButton.setOnAction(e -> {
            helpStage.show();
        });
    }

    void setArraySizeFieldHandler() {
        sizeField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.length() < 9 && newValue.matches("\\d+$")) {
                Integer parsedArraySize = Integer.parseInt(newValue);
                if (parsedArraySize >= 0 && parsedArraySize <= 250) {
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
            setEnabled(prevButton, false);
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
