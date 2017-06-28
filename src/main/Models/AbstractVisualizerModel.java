
public abstract class AbstractVisualizerModel extends Thread implements ISortVisualizer {

    StateSaverModel states = new StateSaverModel();

    public void setSortStates(StateSaverModel states) { this.states = states;}
    public abstract int getCurrentState();
}
