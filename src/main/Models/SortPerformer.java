
public abstract class SortPerformer {
    StateSaverModel saver = new StateSaverModel();
    abstract void setArray(int[] array);
    abstract void performSort();

    public StateSaverModel getSaver() {
        return saver;
    }
}
