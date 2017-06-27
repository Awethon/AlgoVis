import java.util.Random;

public class IntArrayGenerator {
    private int arraySize = 0;
    private String generationMode;


    public int[] generate(int arraySize, String generationMode) {
        if(generationMode.equals("Random"))
            return generateRandom(arraySize);
        else
            return generateReverse(arraySize);
    }

    private int[] generateRandom(int size)
    {
        Random random = new Random();
        int[] result = new int[size];
        for (int i = 0; i < size; i++)
            result[i] = 15 + random.nextInt(100);
        Memento.save(result);
        return result;
    }

    private int[] generateReverse(int size)
    {
        int[] result = new int[size];
        for (int i = 0; i < size; i++)
            result[i] = size - i + 1;
        Memento.save(result);
        return result;
    }
}
