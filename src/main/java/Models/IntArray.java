package Models;

import javafx.collections.ListChangeListener;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.XYChart;

import java.util.ArrayList;

public class IntArray {
    final private String DEFAULT = "black";



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

    public void addAll(int[] array) {
        ArrayList<XYChart.Data<String, Integer>> temp = new ArrayList<>();
        for (int i = 0; i < array.length; i++) {
            temp.add(new XYChart.Data<>(Integer.toString(i), array[i]));
        }
        arraySeries.getData().addAll(temp);
    }

    public void updateBarChart(BarChart<String, Integer> bc) {
        bc.getData().addAll(arraySeries);
        addHandler(bc);
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
        addAll(arr);
        for (int i = 0; i < size(); i++) {
            changeColor(i, DEFAULT);
        }
    }

    public XYChart.Data<String, Integer> getData(int i) {
        if (i < 0 || i >= size()) throw new ArrayIndexOutOfBoundsException();
        return arraySeries.getData().get(i);
    }

    private void addHandler(BarChart<String, Integer> bc) {
        arraySeries.getData().addListener((ListChangeListener<XYChart.Data<String, Integer>>) c -> {
            bc.setCategoryGap(150.0 / bc.getData().get(0).getData().size());
            bc.layout();
        });
    }
}
