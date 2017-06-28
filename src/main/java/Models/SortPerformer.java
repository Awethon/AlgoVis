package Models;

public abstract class SortPerformer {
    StateSaverModel saver = new StateSaverModel();
    public abstract void setArray(int[] array);
    public abstract void performSort();

    public StateSaverModel getSaver() {
        return saver;
    }
}
