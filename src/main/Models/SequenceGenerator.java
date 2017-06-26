import java.util.Random;

public class SequenceGenerator implements ISequenceGenerator {

    @Override
    public int[] generate(String generationMode, int size) {
        if(generationMode.equals("Random"))
            return generateRandom(size);
        else
            return generateReverse(size);
    }

    private int[] generateRandom(int size)
    {
        Random random = new Random();
        int[] result = new int[size];
        for (int i = 0; i < size; i++)
            result[i] = 15 + random.nextInt(100);
        return result;
    }

    private int[] generateReverse(int size)
    {
        int[] result = new int[size];
        for (int i = 0; i < size; i++)
            result[i] = size - i + 1;
        return result;
    }
}
