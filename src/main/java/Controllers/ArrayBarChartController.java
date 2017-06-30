package Controllers;

import Models.IMediator;
import Models.IntArray;
import Models.Memento;
import Models.SortState;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.chart.BarChart;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Line;

public class ArrayBarChartController implements IMediator {
    @FXML
    private BarChart<String, Integer> bc;
    @FXML
    private Pane bottomPane;

    private IntArray array;

    private int first = -1;
    private int second = -1;


    @FXML
    public void initialize() {
        bc.setLegendVisible(false);
        bc.setAnimated(false);
        bc.setBarGap(0);
        array = new IntArray();
        array.addLast(1);
        array.addLast(5);
        array.addLast(3);
        array.addLast(4);
        array.updateBarChart(bc);
    }

    public BarChart getBC() {
        return bc;
    }

    public IntArray getArray() {
        return array;
    }

    @Override
    public void updateChanges(int firstIndex, int secondIndex, int state) {
        Platform.runLater(() -> {
            if (first != -1) array.changeColor(first, "CHART_COLOR_4");
            if (second != -1) array.changeColor(second, "CHART_COLOR_5");
            array.changeColor(firstIndex, "green");
            array.changeColor(secondIndex, "green");
            first = firstIndex;
            second = secondIndex;
        });
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void mergeStarted(SortState state) {
        if (first != -1) array.changeColor(first, "CHART_COLOR_1");
        if (second != -1) array.changeColor(second, "CHART_COLOR_1");
        first = -1;
        second = -1;

        Platform.runLater(() -> {
            int lo = state.getLeft();
            int mid = lo + state.getFirst().length;
            int hi = state.getRight();

            double minX = getBarX(lo);
            double midX = getBarX(mid);
            double maxX = getBarX(hi + 1);

            createMergeCups(minX, midX, maxX);
            for (int i = lo; i < mid; i++) {
                array.set(i, state.getFirst()[i - lo]);
                array.changeColor(i, "CHART_COLOR_4");
            }
            for (int i = mid; i <= hi; i++) {
                array.set(i, state.getSecond()[i - mid]);
                array.changeColor(i, "CHART_COLOR_5");
            }
        });
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void mergePerformed(SortState state) {
        if (first != -1) array.changeColor(first, "CHART_COLOR_1");
        if (second != -1) array.changeColor(second, "CHART_COLOR_1");
        first = -1;
        second = -1;
        Platform.runLater(() -> {
            bottomPane.getChildren().clear();
            int[] arr = state.getResult();
            for (int i = state.getLeft(); i <= state.getRight(); i++) {
                array.set(i, arr[i - state.getLeft()]);
                array.changeColor(i, "CHART_COLOR_1");
            }
        });
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void resetCalled() {
        Platform.runLater(() -> array.copy(Memento.restore()));
        try {
            Thread.sleep(10);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public double getBarX(int i) {
        double width = array.getData(0).getNode().getBoundsInLocal().getMaxX();
        return 47 + (width + 150.0/array.size())*i;
    }

    private void createMergeCups(double minX, double midX, double maxX) {
        Line cupLine = new Line(minX, 5, maxX, 5);
        Line one   = new Line(minX, 5.0, minX, 0.0);
        Line two   = new Line(midX, 5.0, midX, 0.0);
        Line three = new Line(maxX, 5.0, maxX, 0.0);
        Label text = new Label("Merging");
        text.setTranslateY(10.0);
        text.setTranslateX(minX);
        bottomPane.getChildren().addAll(cupLine, one, two, three, text);
    }
}
