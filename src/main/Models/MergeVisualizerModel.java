/**
 * Created by alexthor on 23.06.17.
 */


public class MergeVisualizerModel extends AbstractVisualizerModel {

    private int numberOfState;
    private boolean onPause;
    private boolean onAbortion;
    private IMediator mediator;

    public MergeVisualizerModel() {
        numberOfState = 0;
        onPause = true;
        onAbortion = false;
    }

    void AcceptChanges(int oldIndex, int newIndex){
        mediator.AcceptChanges(oldIndex, newIndex);
    }

    @Override
    public void start() {
        onPause = false;
    }

    @Override
    public void pause() {
        onPause = true;
    }

    @Override
    public void continueProcess() {
        onPause = false;
    }

    @Override
    public void nextStep() {

    }

    @Override
    public void previousStep() {

    }

    @Override
    public void abort() {

    }
}
