
public abstract class AbstractVisualizerModel extends Thread implements ISortVisualizer {

    private int[] sequence;

    public void setSequence(int[] sequence) {
        this.sequence = sequence;
    }


}
