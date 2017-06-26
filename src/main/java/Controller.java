import javafx.fxml.FXML;
import javafx.scene.chart.XYChart;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import org.controlsfx.control.Notifications;

import java.util.Objects;

public class Controller{

    @FXML
    private ArrayBarChartController arrayBarChartController;
    @FXML
    private ControlBarController controlBarController;

    private MergeVisualizerViewModel viewModel;

    @FXML
    public void initialize() {
        viewModel = controlBarController.viewModel;
        controlBarController.arrayBarChartController = this.arrayBarChartController;
        arrayBarChartController.viewModel = viewModel;
        viewModel.setView(arrayBarChartController);
        controlBarController.getTextField().textProperty().addListener((observable, oldValue, newValue) -> {
            bind();
            backBind();
        });
    }

    private void bind(){
        viewModel.setSequenceLength(controlBarController.getTextField().getText());
    }

    private void backBind(){
        controlBarController.getTextField().setDisable(!viewModel.isLengthFieldEnabled());
    }
}