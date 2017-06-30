package Controllers;

import Models.Command;
import javafx.fxml.FXML;

public class Controller {

    @FXML
    private ArrayBarChartController arrayBarChartController;
    @FXML
    private ControlBarController controlBarController;

    @FXML
    public void initialize() {

        controlBarController.setArraySizeFieldHandler(new BarChartUpdate());
        controlBarController.initializeControlPanelHandlers(arrayBarChartController);
        controlBarController.setGenButtonHandler(arrayBarChartController.getArray());
    }


    class BarChartUpdate implements Command {
        private void updateBarChart() {
            Integer size = controlBarController.getParsedArraySize();
            //arrayBarChartController.getArray().clear();
            arrayBarChartController.getBC().setCategoryGap(150.0 / size);
            arrayBarChartController.getBC().layout();
        }

        @Override
        public void execute() {
            updateBarChart();
        }
    }

}