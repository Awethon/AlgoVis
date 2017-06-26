
public class IntArrayGenerator {
    private int arraySize = 0;
    private String generationMode;

    public IntArrayGenerator(int arraySize, String generationMode) {
        this.arraySize = arraySize;
        this.generationMode = generationMode;
    }

    public int[] generate() {
        return new int[] {6, 5, 3, 1, 8, 7, 2, 4, 5};
    }
}
