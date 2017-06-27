
public class MergeVisualizerModel extends AbstractVisualizerModel {

    private volatile int currentState;
    private volatile boolean onNextState = false;
    private volatile boolean onPreviousState = false;
    private volatile boolean onPause = false;
    private volatile boolean onAbortion = false;
    private volatile IMediator mediator;

    public MergeVisualizerModel(IMediator mediator) {
        currentState = 0;
        onAbortion = false;
        onNextState = false;
        onPreviousState = false;
        onPause = false;
        onAbortion = false;
        this.mediator = mediator;
    }

    private void mergePerform(){
        for(currentState = 0; currentState < states.size() && !onAbortion; ++currentState) {
            SortState state = states.getState(currentState);
            mediator.mergeStarted(state);
            if(makeIterate(state))
                return;
            if(onNextState) {
                currentState--;
                onNextState = false;
                continue;
            }
            if(onPreviousState){
                currentState--;
                onPreviousState = false;
                continue;
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

    private boolean makeIterate(SortState state){
        int[] first = state.getFirst(), second = state.getSecond();
        int iter1 = 0, iter2 = 0, firstLength = first.length, secondLength = second.length,
                left = state.getLeft(), targetIter1, targetIter2;
        while (iter1 < firstLength && iter2 < secondLength && !onNextState && !onPreviousState) {
            if(checkPause())
                return true;
            if(onAbortion)
                return true;
            if (first[iter1] <= second[iter2]) {
                iter1++;
            } else {
                iter2++;
            }
            targetIter1 = compareIndex(left, iter1, firstLength);
            targetIter2 = compareIndex(left + firstLength, iter2, secondLength);
            mediator.acceptChanges(targetIter1, targetIter2, currentState);
        }
        while (iter1 < firstLength && !onNextState && !onPreviousState) {
            if(checkPause())
                return true;
            if (onAbortion)
                return true;
            iter1++;
            targetIter1 = compareIndex(left, iter1, firstLength);
            targetIter2 = compareIndex(left + firstLength, iter2, secondLength);
            mediator.acceptChanges(targetIter1, targetIter2, currentState);
        }
        while (iter2 < secondLength && !onNextState && !onPreviousState) {
            if(checkPause())
                return true;
            if (onAbortion)
                return true;
            iter2++;
            targetIter1 = compareIndex(left, iter1, firstLength);
            targetIter2 = compareIndex(left + firstLength, iter2, secondLength);
            mediator.acceptChanges(targetIter1, targetIter2, currentState);
        }
        return false;
    }

    boolean checkPause() {
        while (onPause) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void run(){
        //onPause = false;
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
    public void previousStep() {
        if(currentState > 0) {
            SortState state = states.getState(currentState);
            mediator.mergeStarted(state);
            --currentState;
            state = states.getState(currentState);
            mediator.mergeStarted(state);
            //--currentState;
        }
        onPreviousState = true;
    }

    @Override
    public void nextStep() {
        //onPause = true;
        if(currentState < states.size()) {
            SortState state = states.getState(currentState);
            mediator.mergePerformed(state);
            ++currentState;
        }
        onNextState = true;
    }

    @Override
    public void abort() {
        onAbortion = true;
        mediator.resetCalled();
        interrupt();
    }

    @Override
    public int getCurrentState() {
        return currentState;
    }
}
