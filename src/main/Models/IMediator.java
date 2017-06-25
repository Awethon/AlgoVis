/**
 * Created by alexthor on 25.06.17.
 */
public interface IMediator {
    void acceptChanges(int firstIndex, int secondIndex, int state);
    void mergePerformed(State state);
    void mergeStarted(State state);
}
