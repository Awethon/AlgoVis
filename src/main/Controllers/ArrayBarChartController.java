import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.XYChart;

public class ArrayBarChartController implements IMediator {
    @FXML
    private BarChart<String, Integer> bc;

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
        //((XYChart.Data) arraySeries.getData().get(2)).getNode().setStyle("-fx-bar-fill: aqua;");
        //array.changeColor(2, "aqua");
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
            if (first != -1 && second != -1) {
                array.changeColor(first, "CHART_COLOR_4");
                array.changeColor(second, "CHART_COLOR_5");
            }
            array.changeColor(firstIndex, "green");
            array.changeColor(secondIndex, "green");
            first = firstIndex;
            second = secondIndex;
        });
        try {
            Thread.sleep(10);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void mergePerformed(SortState state) {
        int[] arr = state.getResult();
        Platform.runLater(() -> {
            for (int i = state.getLeft(); i <= state.getRight(); i++) {
                array.set(i, arr[i - state.getLeft()]);
                array.changeColor(i, "CHART_COLOR_1");
            }
        });
        try {
            Thread.sleep(10);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void mergeStarted(SortState state) {
        int lo = state.getLeft();
        int mid = lo + state.getFirst().length;
        int hi = state.getRight();

        Platform.runLater(() -> {
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
            Thread.sleep(10);
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
}
