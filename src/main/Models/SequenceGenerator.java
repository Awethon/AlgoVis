/**
 * Created by alexthor on 25.06.17.
 */
public class SequenceGenerator implements ISequenceGenerator {

    @Override
    public int[] generate(String generationMode) {
        int[] result = new int[1];
        result[0] = 1;
        return result;
    }
}
