package Models;

public class Memento {
    private static int[] meme;

    public static void save(int[] arr) {
        meme = arr.clone();
    }

    public static int[] restore() {
        return meme;
    }
}