/**
 * Created by alexthor on 25.06.17.
 */
public interface ISortPerformer {
    void setSequence(int[] sequence);
    StateSaverModel performSort();
}
