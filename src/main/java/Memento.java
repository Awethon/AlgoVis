public class Memento {
    private static int[] meme;

    static void save(int[] arr) {
        meme = arr.clone();
    }

    static int[] restore() {
        return meme;
    }
}