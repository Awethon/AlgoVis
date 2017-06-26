
public interface IMediator {

    /**
     * Метод предназначен для обработки изменившегося состояния,
     * здесь рекомендуется обработать перекраску столбца, перед этим убрав окраску
     * у старого.
     * @param firstIndex индекс в первом подмассиве
     * @param secondIndex индекс во втором подмассиве
     * @param state номер текущего шага, по нему можно получить текущее состояние из viewModel
     * */
    void updateChanges(int firstIndex, int secondIndex, int state);

    /**
     * Вызывается, когда завершается метод Merge, т.е. два подмассива были объедены,
     * здесь стоит очистить окраску столбцов, а также изменить значения от
     * state.getLeft() до state.getLeft() на значения
     * state.getResult() - результат операции Merge для данного состояния.
     * @param state текущее завершенное состояние, содержит границы промежутка
     *              и результат операции Merge.
     */
    void mergePerformed(State state);

    /**
     * Вызывается, когда начинается операция Merge. Здесь рекомендуется проинициализировать
     * индексы для столбцов и выделить их цветом, также привести участок от
     * state.getLeft() до state.getRight() к состоянию содержащему последовательно массивы
     * state.getFirst() и state.getSecond().
     * @param state текущее начатое состояние, содержит границы промежутка
     *              и результат операции Merge.
     */
    void mergeStarted(State state);
}
