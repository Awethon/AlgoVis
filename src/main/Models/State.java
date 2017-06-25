/**
 * Created by alexthor on 25.06.17.
 */
public class State {
    private int[] first;
    private int[] second;
    private int left;
    private int right;

    public State(int[] first, int[] second, int left, int right) {
        this.first = first;
        this.second = second;
        this.left = left;
        this.right = right;
    }

    public int[] getFirst() {
        return first;
    }

    public int[] getSecond() {
        return second;
    }

    public int getLeft() {
        return left;
    }


    public int getRight() {
        return right;
    }

}
