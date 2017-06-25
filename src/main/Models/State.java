/**
 * Created by alexthor on 25.06.17.
 */
public class State {
    //Левый подмассив
    private int[] first;
    //Правый подмассив
    private int[] second;
    //Левый конец всего участка
    private int left;
    //Правый конец всего участка
    private int right;
    //Результат шага
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
