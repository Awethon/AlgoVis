import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;

public class ControlBarController implements IMediator {

    @FXML
    private ArrayBarChartController arrayBarChartController;
    //@FXML
    //private ControlBarController controlBarController;
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
    private MergeVisualizerViewModel viewModel;
    private String lengthFieldText = "";
    @FXML
    void initialize() {
        viewModel = new MergeVisualizerViewModel();
        viewModel.setGenerator(new SequenceGenerator());
        viewModel.setSortPerformer(new MergeSortPerformerModel());
        viewModel.setView(this);
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
            bind();
            backBind();
        });

        tf.textProperty().addListener((observable, oldValue, newValue) -> {
            lengthFieldText = newValue;
            bind();
            backBind();
        });

        setButtonHandler(genButton, (e) -> {
            viewModel.generateSequence();
            backBind();
        });

        setButtonHandler(startButton, (e) -> {
            viewModel.start();
            backBind();
        });

        setButtonHandler(pauseButton, (e) -> {
            viewModel.pause();
            backBind();
        });

        setButtonHandler(nextButton, (e) -> {
            viewModel.nextStep();
            backBind();
        });

        setButtonHandler(resetButton, (e) -> {
            viewModel.abort();
            backBind();
        });
    }

    private void bind() {
        viewModel.setSequenceLength(tf.getText());
        viewModel.setGenerationMode(((RadioButton)tg.getSelectedToggle()).getText());
    }

    private void backBind() {
        textArray.setDisable(!viewModel.isCustomFieldEnabled());
        tf.setDisable(!viewModel.isLengthFieldEnabled());
        genButton.setDisable(!viewModel.isGenerateButtonEnabled());
        startButton.setDisable(!viewModel.isStartButtonEnabled());
        pauseButton.setDisable(!viewModel.isPauseButtonEnabled());
        nextButton.setDisable(!viewModel.isNextButtonEnabled());
        resetButton.setDisable(!viewModel.isAbortButtonEnabled());
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

    public TextField getTextArray() {
        return textArray;
    }

    public Button getGenButton() {
        return genButton;
    }

    @Override
    public void acceptChanges(int firstIndex, int secondIndex, int state) {

    }

    @Override
    public void mergePerformed(SortState sortState) {

    }

    @Override
    public void mergeStarted(SortState sortState) {

    }

    @Override
    public void resetCalled() {

    }
}
