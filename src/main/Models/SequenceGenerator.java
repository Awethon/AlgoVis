/**
 * Created by alexthor on 25.06.17.
 */
public class SequenceGenerator implements ISequenceGenerator {

    @Override
    public int[] generate(String generationMode) {
        int[] result = new int[] {6, 5, 3, 1, 8, 7, 2, 4, 5};

        return result;
    }
}
