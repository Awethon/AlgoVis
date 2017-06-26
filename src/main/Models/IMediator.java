
public interface IMediator {

    /**
     * Метод предназначен для обработки изменившегося состояния,
     * здесь рекомендуется обработать перекраску столбца, перед этим убрав окраску
     * у старого.
     * @param firstIndex индекс в первом подмассиве
     * @param secondIndex индекс во втором подмассиве
     * @param state номер текущего шага, по нему можно получить текущее состояние из viewModel
     * */
    void acceptChanges(int firstIndex, int secondIndex, int state);

    /**
     * Вызывается, когда завершается метод Merge, т.е. два подмассива были объедены,
     * здесь стоит очистить окраску столбцов, а также изменить значения от
     * sortState.getLeft() до sortState.getLeft() на значения
     * sortState.getResult() - результат операции Merge для данного состояния.
     * @param sortState текущее завершенное состояние, содержит границы промежутка
     *              и результат операции Merge.
     */
    void mergePerformed(SortState sortState);

    /**
     * Вызывается, когда начинается операция Merge. Здесь рекомендуется проинициализировать
     * индексы для столбцов и выделить их цветом, также привести участок от
     * sortState.getLeft() до sortState.getRight() к состоянию содержащему последовательно массивы
     * sortState.getFirst() и sortState.getSecond().
     * @param sortState текущее начатое состояние, содержит границы промежутка
     *              и результат операции Merge.
     */
    void mergeStarted(SortState sortState);

    /**
     * Вызывается, когда процесс визуализации был прерван методом abort() объекта ISortVisualizer.
     */
    void resetCalled();
}
