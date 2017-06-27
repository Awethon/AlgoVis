import java.util.Random;

public class SequenceGenerator implements ISequenceGenerator {

    @Override
    public int[] generate(String generationMode, int size) {
        if(generationMode.equals("Random"))
            return generateRandom(size);
        else if(generationMode.equals("Almost sorted"))
            return generateAlmostSorted(size);
        else
            return generateReverse(size);
    }

    private int[] generateRandom(int size)
    {
        Random random = new Random();
        int[] result = new int[size];
        for (int i = 0; i < size; i++)
            result[i] = random.nextInt(size);
        return result;
    }

    private int[] generateReverse(int size)
    {
        int[] result = new int[size];
        for (int i = 0; i < size; i++)
            result[i] = size - i + 1;
        return result;
    }

    private int[] generateAlmostSorted(int size)
    {
        Random random = new Random();
        int[] result = new int[size];
        for (int i = 0; i < size-1; i++)
            result[i] = i;

        result[random.nextInt(size)] = random.nextInt(size);

        return result;
    }
}
