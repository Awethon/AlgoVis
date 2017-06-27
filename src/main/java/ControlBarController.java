import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;

public class ControlBarController {

    //@FXML
    public volatile ArrayBarChartController arrayBarChartController;
    //@FXML
    //private ControlBarController controlBarController;
    @FXML
    private TextField sizeTextField;
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

    private ToggleGroup toggleMenu;
    private TextField customTextArray;
    public volatile MergeVisualizerViewModel viewModel;
    private String lengthFieldText = "";

    private Button previousButton;

    @FXML
    void initialize() {
        viewModel = new MergeVisualizerViewModel();
        viewModel.setGenerator(new SequenceGenerator());
        viewModel.setSortPerformer(new MergeSortPerformerModel());
        //viewModel.setVisualizerModel(new MergeVisualizerModel(viewModel));
        startButton.setGraphic(new ImageView(new Image(getClass().getResourceAsStream("/play3.png"))));
        pauseButton.setGraphic(new ImageView(new Image(getClass().getResourceAsStream("/pause2.png"))));
        nextButton.setGraphic(new ImageView(new Image(getClass().getResourceAsStream("/arrow-right2.png"))));
        resetButton.setGraphic(new ImageView(new Image(getClass().getResourceAsStream("/rotate.png"))));

        RadioButton[] rbs = new RadioButton[4];
        toggleMenu = new ToggleGroup();
        rbs[0] = new RadioButton("Random");
        rbs[1] = new RadioButton("Almost sorted");
        rbs[2] = new RadioButton("Reversed");
        rbs[3] = new RadioButton("Custom");
        toggleMenu.getToggles().addAll(rbs);
        rbs[0].setSelected(true);
        radioVBox.getChildren().addAll(rbs);
        customTextArray = new TextField();
        radioVBox.getChildren().add(customTextArray);
        customTextArray.setDisable(true);

        toggleMenu.selectedToggleProperty().addListener((observable, oldValue, newValue) -> {
            bind();
            backBind();
        });


        sizeTextField.textProperty().addListener((observable, oldValue, newValue) -> {
            lengthFieldText = newValue;
            bind();
            backBind();
        });

        setButtonHandler(genButton, (e) -> {
            viewModel.generateSequence();
            bindSequence(viewModel.getSequence());
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
            //viewModel.previousStep();
            backBind();
        });
        /*
        setButtonHandler(previousButton, (e) ->{
            viewModel.previousStep();
            backBind();
        });*/

        setButtonHandler(resetButton, (e) -> {
            viewModel.abort();
            backBind();
        });
    }

    private void bind() {
        viewModel.setSequenceLength(sizeTextField.getText());
        viewModel.setGenerationMode(((RadioButton) toggleMenu.getSelectedToggle()).getText());
    }

    private void backBind() {
        customTextArray.setDisable(!viewModel.isCustomFieldEnabled());
        sizeTextField.setDisable(!viewModel.isLengthFieldEnabled());
        genButton.setDisable(!viewModel.isGenerateButtonEnabled());
        startButton.setDisable(!viewModel.isStartButtonEnabled());
        pauseButton.setDisable(!viewModel.isPauseButtonEnabled());
        nextButton.setDisable(!viewModel.isNextButtonEnabled());
        resetButton.setDisable(!viewModel.isAbortButtonEnabled());
    }

    private void bindSequence(int[] sequence){
        IntArray array = arrayBarChartController.getArray();
        array.clear();
        for (int i = 0; i < sequence.length; i++)
            array.addLast(sequence[i]);
    }

    public TextField getTextField() {
        return sizeTextField;
    }

    public void genButtonSetHandler(EventHandler<ActionEvent> value) {
        genButton.setOnAction(value);
    }

    public void setButtonHandler(Button button, EventHandler<ActionEvent> value){
        button.setOnAction(value);
    }

    public Toggle getSelectedToggle(){
        return toggleMenu.getSelectedToggle();
    }

    public TextField getCustomTextArray() {
        return customTextArray;
    }

    public Button getGenButton() {
        return genButton;
    }

}
