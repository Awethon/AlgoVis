package Controllers;

import javafx.fxml.FXML;

public class Controller {

    @FXML
    private ArrayBarChartController arrayBarChartController;
    @FXML
    private ControlBarController controlBarController;

    @FXML
    public void initialize() {
        controlBarController.setArraySizeFieldHandler();
        controlBarController.initializeControlPanelHandlers(arrayBarChartController);
        controlBarController.setGenButtonHandler(arrayBarChartController.getArray());
    }
}