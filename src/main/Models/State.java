
public class State {

    private int[] first;

    private int[] second;

    private int left;

    private int right;

    private int[] result;

    public State(int[] first, int[] second, int left, int right, int[] result) {
        this.first = first;
        this.second = second;
        this.left = left;
        this.right = right;
        this.result = result;
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

    public int[] getResult() {
        return result;
    }
}
