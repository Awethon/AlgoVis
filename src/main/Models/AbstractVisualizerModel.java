
public abstract class AbstractVisualizerModel extends Thread implements ISortVisualizer {

    //protected int[] sequence;
    protected StateSaverModel states;

    /*
    public void setSequence(int[] sequence) {
        this.sequence = sequence;
    }*/

    public void setSortStates(StateSaverModel states) { this.states = states;}
}
