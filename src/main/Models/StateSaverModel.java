import java.util.ArrayList;

public class StateSaverModel {

    private ArrayList<State> states;

    /*public ArrayList<State> getStates() {
        return this.states;
    }*/

    public int size(){
        return states.size();
    }

    public StateSaverModel() {
        states = new ArrayList<>();
    }

    public State getState(int index) {
        return states.get(index);
    }

    public void saveState(State state) {
        this.states.add(state);
    }
}
