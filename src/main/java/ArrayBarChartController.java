import com.sun.javafx.tk.Toolkit;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.scene.chart.BarChart;

public class ArrayBarChartController implements IMediator{
    @FXML
    private BarChart<String, Integer> bc;

    public MergeVisualizerViewModel viewModel;

    private IntArray array;

    private int first = -1;
    private int second = -1;

    @FXML
    public void initialize() {
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
    public void acceptChanges(int firstIndex, int secondIndex, int state) {
        Platform.runLater(() -> {
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
                array.changeColor(i, "orange");
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
        first = state.getLeft();
        second = state.getLeft() + state.getFirst().length;
        int lo = state.getLeft();
        int mid = state.getFirst().length;
        int hi = state.getRight();

        Platform.runLater(() -> {
            for (int i = 0; i < state.getFirst().length; i++) {
                array.set(i + state.getLeft(), state.getFirst()[i]);
                //array.changeColor(i, "yellow");
            }
            for (int i = 0; i < state.getSecond().length; i++) {
                array.set(i + state.getLeft() + state.getFirst().length, state.getSecond()[i]);
                //array.changeColor(i, "red");
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
        Platform.runLater(() -> {
            bindSequence(viewModel.getSequence());
        });
        try {
            Thread.sleep(10);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void bindSequence(int[] sequence){
        IntArray array = getArray();
        array.clear();
        for (int i = 0; i < sequence.length; i++)
            array.addLast(sequence[i]);
    }
}
