
public class MergeVisualizerModel extends AbstractVisualizerModel {

    private volatile int currentState;
    private volatile boolean onPause;
    private volatile boolean onAbortion;
    private IMediator mediator;
    //private StateSaverModel states;

    public MergeVisualizerModel(IMediator mediator) {
        currentState = 0;
        onPause = true;
        onAbortion = false;
        this.mediator = mediator;
    }

    private void mergePerform(){
        for(currentState = 0; currentState < states.size(); ++currentState) {
            SortState state = states.getState(currentState);
            int[] first = state.getFirst(), second = state.getSecond();
            int iter1 = 0, iter2 = 0, firstLength = first.length, secondLength = second.length,
                    left = state.getLeft(), targetIter1, targetIter2;
            if (Thread.interrupted())
                return;
            mediator.mergeStarted(state);
            while (iter1 < firstLength && iter2 < secondLength) {
                while (onPause) {
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        mediator.resetCalled();
                        return;
                    }
                }
                if (Thread.interrupted())
                    return;
                if (first[iter1] <= second[iter2]) {
                    iter1++;
                } else {
                    iter2++;
                }
                targetIter1 = compareIndex(left, iter1, firstLength);
                targetIter2 = compareIndex(left + firstLength, iter2, secondLength);
                mediator.acceptChanges(targetIter1, targetIter2, currentState);
            }
            while (iter1 < firstLength) {
                while (onPause) {
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        mediator.resetCalled();
                        return;
                    }
                }
                if (Thread.interrupted())
                    return;
                iter1++;
                targetIter1 = compareIndex(left, iter1, firstLength);
                targetIter2 = compareIndex(left + firstLength, iter2, secondLength);
                mediator.acceptChanges(targetIter1, targetIter2, currentState);
            }
            while (iter2 < secondLength) {
                while (onPause) {
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        mediator.resetCalled();
                        return;
                    }
                }
                if (Thread.interrupted())
                    return;
                iter2++;
                targetIter1 = compareIndex(left, iter1, firstLength);
                targetIter2 = compareIndex(left + firstLength, iter2, secondLength);
                mediator.acceptChanges(targetIter1, targetIter2, currentState);
            }
            mediator.mergePerformed(state);
        }
    }

    private int compareIndex(int left, int index, int length){
        if(index < length)
            return left + index;
        else
            return left + length - 1;
    }

    @Override
    public void run(){
        onPause = false;
        mergePerform();
    }

    @Override
    public void startVisualize() {
        mergePerform();
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
