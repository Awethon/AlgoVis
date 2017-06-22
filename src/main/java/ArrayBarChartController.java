import javafx.fxml.FXML;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.XYChart;

public class ArrayBarChartController {
    @FXML
    private BarChart bc;

    private XYChart.Series arraySeries;

    public void initialize() {
        bc.setAnimated(false);
        bc.setBarGap(0);
        arraySeries = new XYChart.Series();
        arraySeries.setName("Array");
        arraySeries.getData().add(new XYChart.Data("0", 1));
        arraySeries.getData().add(new XYChart.Data("1", 2));
        arraySeries.getData().add(new XYChart.Data("2", 3));
        arraySeries.getData().add(new XYChart.Data("3", 4));
        bc.getData().addAll(arraySeries);
        ((XYChart.Data) arraySeries.getData().get(2)).getNode().setStyle("-fx-bar-fill: aqua;");
    }

    public BarChart getBC() {
        return bc;
    }

    public void clear() {
        arraySeries.getData().clear();
    }

    public void get(int i) {
    }

    public void add(XYChart.Data data) {
        arraySeries.getData().add(data);
    }

}
