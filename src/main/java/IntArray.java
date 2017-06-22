import javafx.scene.chart.BarChart;
import javafx.scene.chart.XYChart;

import java.util.ArrayList;
import java.util.List;

public class IntArray {
    private XYChart.Series<String, Integer> arraySeries = new XYChart.Series<>();
    private List<Integer> array = new ArrayList<>();

    public IntArray() {
        arraySeries.setName("Array");
    }

    public int size() {
        return array.size();
    }
    public void set(int i, int value) {
        if (i < 0 || i >= size()) throw new ArrayIndexOutOfBoundsException();
        array.set(i, value);
        arraySeries.getData().set(i, new XYChart.Data<>(Integer.toString(i), value));
    }
    public void addLast(int value) {
        array.add(value);
        arraySeries.getData().add(new XYChart.Data<>(Integer.toString(size()),  value));
    }

    public void updateBarChart(BarChart<String, Integer> bc) {
        bc.getData().addAll(arraySeries);
    }

    public void clear() {
        array.clear();
        arraySeries.getData().clear();
    }

    public void changeColor(int i, String cssColor) {
        if (i < 0 || i >= size()) throw new ArrayIndexOutOfBoundsException();
        arraySeries.getData().get(i).getNode().setStyle("-fx-bar-fill: " + cssColor + ";");
    }
}
