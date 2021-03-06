package Models;

import javafx.application.Platform;
import javafx.beans.binding.BooleanBinding;
import javafx.beans.property.SimpleBooleanProperty;

public class MergeVisualizerModel extends AbstractVisualizerModel {

    private volatile int currentState;
    private volatile boolean onNextState = false;
    private volatile boolean onPreviousState = false;
    private volatile boolean onPause = false;
    private volatile boolean onAbortion = false;
    private volatile IMediator mediator;

    private SimpleBooleanProperty done = new SimpleBooleanProperty(false);

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
        for(currentState = 0; currentState < states.size() + 1 && !onAbortion; ++currentState) {
            SortState state = null;
            if(states.size() == currentState){
                done.set(true);
                onPause = true;
                if(sleepOnPause())
                    return;
            }
            state = states.getState(currentState);
            mediator.mergeStarted(state);
            if (makeIterate(state)) return;
            if (onNextState) {
                currentState--;
                onNextState = false;
                continue;
            }
            if (onPreviousState){
                currentState--;
                onPreviousState = false;
                continue;
            }
            mediator.mergePerformed(state);
        }
        done.set(true);
    }

    private boolean makeIterate(SortState state){
        int[] first = state.getFirst();
        int[] second = state.getSecond();
        int iter1 = 0, iter2 = 0, firstLength = first.length, secondLength = second.length, left = state.getLeft();
        while (iter1 < firstLength && iter2 < secondLength && !onNextState && !onPreviousState) {
            if(sleepOnPause())
                return true;
            if(onAbortion)
                return true;
            if (first[iter1] <= second[iter2]) {
                iter1++;
            } else {
                iter2++;
            }
            doStuff(iter1, iter2, firstLength, secondLength, left);
        }
        while (iter1 < firstLength && !onNextState && !onPreviousState) {
            if(sleepOnPause())
                return true;
            if (onAbortion)
                return true;
            iter1++;
            doStuff(iter1, iter2, firstLength, secondLength, left);
        }

        while (iter2 < secondLength && !onNextState && !onPreviousState) {
            if(sleepOnPause())
                return true;
            if (onAbortion)
                return true;
            iter2++;
            doStuff(iter1, iter2, firstLength, secondLength, left);
        }
        return false;
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
                sleep(1000);
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
        Object o = new Object();
        return false;
    }

    @Override
    public void run(){ mergePerform(); }

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
            if (currentState == states.size()) --currentState;
            SortState state = states.getState(currentState);
            mediator.mergeStarted(state);
            --currentState;
            state = states.getState(currentState);
            mediator.mergeStarted(state);
            done.set(false);
        }
        onPreviousState = true;
        Platform.runLater(() -> {
            SortState state = states.getState(currentState);
            mediator.fixColor(state);
        });
    }

    @Override
    public void nextStep() {
        if(currentState < states.size()) {
            SortState state = states.getState(currentState);
            mediator.mergePerformed(state);
            ++currentState;
            done.set(false);
            if(currentState < states.size())
                Platform.runLater(() -> {
                    mediator.fixColor(states.getState(currentState));
                });
        }
        else {
            done.set(false);
            done.set(true);
        }
        onNextState = true;
    }

    public int getSize(){
        return states.size();
    }

    @Override
    public void reset() {
        onAbortion = true;
        mediator.resetCalled();
        interrupt();
    }

    @Override
    public int getCurrentState() {
        return currentState;
    }

    public boolean isAtStart() {
        return currentState <= 0;
    }


    public BooleanBinding getVisualizationOverProperty() {
        return done.isEqualTo(new SimpleBooleanProperty(true));
    }
}