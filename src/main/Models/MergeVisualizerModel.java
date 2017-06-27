public class MergeVisualizerModel extends AbstractVisualizerModel {

    private volatile int currentState;
    private volatile boolean onNextState = false;
    private volatile boolean onPreviousState = false;
    private volatile boolean onPause;
    private volatile boolean onAbortion = false;
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
            if (onAbortion) return;
            mediator.mergeStarted(state);
            if (checkNextState(state)) continue;
            while (iter1 < firstLength && iter2 < secondLength) {
                if(sleepOnPause())
                    return;
                if(checkNextState(state))
                    continue;
                if (onAbortion) {
                    return;
                }
                if (first[iter1] <= second[iter2]) {
                    iter1++;
                } else {
                    iter2++;
                }
                doStuff(iter1, iter2, firstLength, secondLength, left);
            }
            while (iter1 < firstLength) {
                if(sleepOnPause())
                    return;
                if(checkNextState(state))
                    continue;
                if (onAbortion)
                    return;
                iter1++;
                doStuff(iter1, iter2, firstLength, secondLength, left);
            }
            while (iter2 < secondLength) {
                if(sleepOnPause())
                    return;
                if(checkNextState(state))
                    continue;
                if (onAbortion)
                    return;
                iter2++;
                doStuff(iter1, iter2, firstLength, secondLength, left);
            }
            mediator.mergePerformed(state);
        }
    }

    private void doStuff(int iter1, int iter2, int firstLength, int secondLength, int left) {
        int targetIter1 = compareIndex(left, iter1, firstLength);
        int targetIter2 = compareIndex(left + firstLength, iter2, secondLength);
        mediator.updateChanges(targetIter1, targetIter2, currentState);
    }

    private int compareIndex(int left, int index, int length) {
        if(index < length)
            return left + index;
        else
            return left + length - 1;
    }

    private boolean sleepOnPause() {
        while (onPause) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                mediator.resetCalled();
                return true;
            }
        }
        return false;
    }

    private boolean checkNextState(SortState state) {
        if(onNextState) {
            mediator.mergePerformed(state);
            onNextState = false;
            onPause = true;
            return true;
        }
        return false;
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
        onPause = false;
    }

    @Override
    public void previousStep() {

    }

    @Override
    public void reset() {
        onAbortion = true;
        mediator.resetCalled();
        interrupt();
    }
}