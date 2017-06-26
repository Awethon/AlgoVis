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


    @FXML
    public void initialize() {

        controlBarController.getTextField().textProperty().addListener((observable, oldValue, newValue) -> {
            if (Objects.equals(newValue, "")) {
                controlBarController.getGenButton().setDisable(true);
                return;
            }
            if (newValue.length() < 9 && newValue.matches("\\d+$")) {
                Integer n = Integer.parseInt(newValue);

                if (n >= 0 && n <= 250) {
                    /*arrayBarChartController.getArray().clear();
                    arrayBarChartController.getBC().setCategoryGap(150.0 / n);
                    arrayBarChartController.getBC().layout();
                    for (int i = 0; i < n; i++) {
                        arrayBarChartController.getArray().addLast(i + 1);
                    }
                    controlBarController.getGenButton().setDisable(false);*/
                } else {
                    controlBarController.getGenButton().setDisable(true);
                    Notifications.create().title("Length error").text("Array size must be 1-250").showError();
                }
            } else {
                controlBarController.getGenButton().setDisable(true);
                Notifications.create().title("Input data error").text("Field must be filled with number from 1 to 250").showError();
            }
        });
/*
        controlBarController.genButtonSetHandler((e) -> {
            String text = ((RadioButton) (controlBarController.getSelectedToggle())).getText();
            if (text.equals("Random")) {

            } else if (text.equals("Almost sorted")) {

            } else if (text.equals("Reversed")) {


            }
        });*/
    }
}