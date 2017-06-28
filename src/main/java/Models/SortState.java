package Models;

/**
 * Класс представляет собой состояние сортируемой последовательности для каждого шага
 * процесса слияния. На каждом шаге храняться первый и второй подмассивы для слияния,
 * индексы начала и конца участка слияния, а также результат слияния.
 */
public class SortState {

    private int[] first;

    private int[] second;

    private int left;

    private int right;

    private int[] result;

    /**
     * Конструктор SortState
     * @param first первый подмассив
     * @param second второй подмассив
     * @param left индекс начала участка слияния
     * @param right индекс конца участка слияния
     * @param result результат слияния
     */
    public SortState(int[] first, int[] second, int left, int right, int[] result) {
        this.first = first;
        this.second = second;
        this.left = left;
        this.right = right;
        this.result = result;
    }

    /**
     * Геттер первого массива для слияния на текущем шаге
     * @return массив отсортированный по возрастанию
     */
    public int[] getFirst() {
        return first;
    }

    /**
     * Геттер второго массива для слияния на текущем шаге
     * @return массив отсортированный по возрастанию
     */
    public int[] getSecond() {
        return second;
    }

    /**
     * Геттер индекса начала слияния на текущем шаге,
     * для начальной последовательности
     * @return индекс начала участка слияния
     */
    public int getLeft() {
        return left;
    }

    /**
     * Геттер индекса конца слияния на текущем шаге,
     * для начальной последовательности
     * @return индекс конца участка слияния
     */
    public int getRight() {
        return right;
    }

    /**
     * Геттер результата слияния первого и второго подмассива на текущем шаге
     * @return массив отсортированный по возрастанию
     */
    public int[] getResult() {
        return result;
    }
}
