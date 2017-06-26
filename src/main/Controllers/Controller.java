import javafx.fxml.FXML;
import javafx.scene.chart.XYChart;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import org.controlsfx.control.Notifications;

import java.util.Objects;

public class Controller {

    @FXML
    private ArrayBarChartController arrayBarChartController;
    @FXML
    private ControlBarController controlBarController;

    private static MergeVisualizerModel model;

    private Integer parsedArraySize = -1;

    @FXML
    public void initialize() {
        model = new MergeVisualizerModel(arrayBarChartController);
        initializeEventHandlers();
    }

    private void initializeEventHandlers() {
        controlBarController.getTextField().textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.length() < 9 && newValue.matches("\\d+$")) {
                parsedArraySize = Integer.parseInt(newValue);
                if (parsedArraySize >= 0 && parsedArraySize <= 250) {
                    updateBarChart(parsedArraySize);
                    controlBarController.getGenButton().setDisable(false);
                } else {
                    controlBarController.getGenButton().setDisable(true);
                    Notifications.create().title("Length error").text("Array size must be 1-250").showError();
                }
            } else {
                controlBarController.getGenButton().setDisable(true);
                if (newValue.length() > 0) Notifications.create().title("Input data error").text("Field must be filled with number from 1 to 250").showError();
            }
        });

        controlBarController.genButtonSetHandler((e) -> {
            String text = controlBarController.getSelectedMode();
            IntArrayGenerator generator = new IntArrayGenerator(parsedArraySize, text);
            generator.generate(); //get return value in model
        });
    }

    private void updateBarChart(Integer n) {
        arrayBarChartController.getArray().clear();
        arrayBarChartController.getBC().setCategoryGap(150.0 / n);
        arrayBarChartController.getBC().layout();
        for (int i = 0; i < n; i++) {
            arrayBarChartController.getArray().addLast(i + 1);
        }
    }
}