


public class MergeVisualizerModel extends AbstractVisualizerModel {

    private volatile int currentState;
    private volatile boolean onPause;
    private volatile boolean onAbortion;
    private IMediator mediator;
    private StateSaverModel states;

    public MergeVisualizerModel(IMediator mediator) {
        currentState = 0;
        onPause = true;
        onAbortion = false;
        this.mediator = mediator;
    }

    private void mergePerfom(){
        for(currentState = 0; currentState < states.size(); ++currentState) {
            State state = states.getState(currentState);
            int[] first = state.getFirst(), second = state.getSecond();
            int iter1 = 0, iter2 = 0, firstLength = first.length, secondLength = second.length, left = state.getLeft();
            mediator.mergeStarted(state);
            while (iter1 < firstLength && iter2 < secondLength) {
                if (first[iter1] <= second[iter2]) {
                    iter1++;
                } else {
                    iter2++;
                }
                mediator.acceptChanges(left + iter1, left + iter2, currentState);
            }
            while (iter1 < firstLength) {
                iter1++;
                mediator.acceptChanges(left + iter1, left + iter2, currentState);
            }
            while (iter2 < secondLength) {
                iter2++;
                mediator.acceptChanges(left + iter1, left + iter2, currentState);
            }
            mediator.mergePerformed(state);
        }
    }


    @Override
    public void start() {
        mergePerfom();
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
