import javafx.scene.chart.BarChart;
import javafx.scene.chart.XYChart;

public class IntArray {
    private XYChart.Series<String, Integer> arraySeries = new XYChart.Series<>();

    public IntArray() {
        arraySeries.setName("Array");
    }

    public int size() {
        return arraySeries.getData().size();
    }
    public void set(int i, int value) {
        if (i < 0 || i >= size()) throw new ArrayIndexOutOfBoundsException();
        arraySeries.getData().set(i, new XYChart.Data<>(Integer.toString(i), value));
    }
    public int get(int i) {
        if (i < 0 || i >= size()) throw new ArrayIndexOutOfBoundsException();
        return arraySeries.getData().get(i).getYValue();
    }
    public void addLast(int value) {
        arraySeries.getData().add(new XYChart.Data<>(Integer.toString(size()),  value));
    }

    public void updateBarChart(BarChart<String, Integer> bc) {
        bc.getData().addAll(arraySeries);
    }

    public void clear() {
        arraySeries.getData().clear();
    }

    public void changeColor(int i, String cssColor) {
        if (i < 0 || i >= size()) throw new ArrayIndexOutOfBoundsException();
        arraySeries.getData().get(i).getNode().setStyle("-fx-bar-fill: " + cssColor + ";");
    }

    public void copy(int[] arr) {
        clear();
        for (int item : arr) {
            addLast(item);
        }
    }

    public XYChart.Data<String, Integer> getData(int i) {
        if (i < 0 || i >= size()) throw new ArrayIndexOutOfBoundsException();
        return arraySeries.getData().get(i);
    }
}
