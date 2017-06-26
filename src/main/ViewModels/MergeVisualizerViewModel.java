
public class MergeVisualizerViewModel implements IMediator {

    //Генератор исходных данных
    ISequenceGenerator generator;
    //Производит сортировку и сохраняет шаги в объект StateSaverModel
    ISortPerformer sortPerformer;
    //Содержит шаги сортировки
    StateSaverModel model;
    //Производит пошаговое прохождение сортировки и уведомляет о изменениях
    AbstractVisualizerModel visualizerModel;
    //Производит принятие обновленных данных
    IMediator view;

    private boolean startButtonEnabled = false;
    private boolean pauseButtonEnabled = false;
    private boolean abortButtonEnabled = false;
    private boolean previousButtonEnabled = false;
    private boolean nextButtonEnabled = false;
    private boolean generateButtonEnabled = false;
    private boolean customFieldEnabled = false;

    private int sequenceLength = 0;
    private String generationMode;

    private int[] sequence;
    private boolean lengthFieldEnabled = true;


    public void setView(IMediator view){
        this.view = view;
    }

    public void setGenerator(ISequenceGenerator generator)
    {
        this.generator = generator;
    }

    public void setSortPerformer(ISortPerformer sortPerformer){
        this.sortPerformer = sortPerformer;
    }

    public boolean isStartButtonEnabled() {
        return startButtonEnabled;
    }

    public boolean isPauseButtonEnabled() {
        return pauseButtonEnabled;
    }

    public boolean isNextButtonEnabled() {
        return nextButtonEnabled;
    }

    public boolean isPreviousButtonEnabled() {
        return previousButtonEnabled;
    }

    public boolean isAbortButtonEnabled() {
        return abortButtonEnabled;
    }

    public boolean isGenerateButtonEnabled() {
        return generateButtonEnabled;
    }

    public boolean isCustomFieldEnabled(){
        return customFieldEnabled;
    }

    public boolean isLengthFieldEnabled(){
        return lengthFieldEnabled;
    }

    /**
     *
     * @param input
     */
    public void setSequenceLength(String input) {
        if (input.equals("")) {
            generateButtonEnabled = false;
            return;
        }
        if (input.length() > 9 || !input.matches("\\d+$")) {
            generateButtonEnabled = false;
            return;
        }
        Integer intInput = Integer.parseInt(input);

        if (intInput <= 0 && intInput > 250) {
            generateButtonEnabled = false;
            return;
        }
        sequenceLength = intInput;
        generateButtonEnabled = true;
    }
    //Вызывается по нажатию клавиши generate
    public void generateSequence() {

        this.sequence = generator.generate(generationMode);
        sortPerformer.setSequence(this.sequence);
        model = sortPerformer.performSort();
        startButtonEnabled = true;
        nextButtonEnabled = true;
    }
    //Вызывается при изменении типа генератора
    public void setGenerationMode(String generationMode) {

        this.generationMode = generationMode;
        if(generationMode.equals("Custom")) {
            generateButtonEnabled = false;
            customFieldEnabled = true;
            lengthFieldEnabled = false;
            return;
        }
        customFieldEnabled = false;
        generateButtonEnabled = true;
        lengthFieldEnabled = true;
        //this.generationMode = generationMode;
    }
    //Вызывается при нажатии start
    public void start() {
        startButtonEnabled = false;
        pauseButtonEnabled = true;
        nextButtonEnabled = false;
        previousButtonEnabled = false;
        abortButtonEnabled = true;

    }
    //Вызывается при нажатии pause
    public void pause() {
        pauseButtonEnabled = false;
        startButtonEnabled = true;
        nextButtonEnabled = true;
        previousButtonEnabled = true;

    }
    //Вызывается при нажатии next
    public void nextStep() {
        abortButtonEnabled = true;
        previousButtonEnabled = true;

    }
    //Вызывается при нажатии previous
    public void previousStep() {

    }
    //Вызывается при нажатии abort
    public void abort() {
        abortButtonEnabled = false;
        pauseButtonEnabled = false;
        previousButtonEnabled = false;
        startButtonEnabled = true;
        nextButtonEnabled = true;
    }

    public int[] getSequence(){
        return sequence;
    }

    @Override
    public void acceptChanges(int firstIndex, int secondIndex, int state) {
        view.acceptChanges(firstIndex, secondIndex, state);
    }

    @Override
    public void mergePerformed(SortState sortState) {
        view.mergePerformed(sortState);
    }

    @Override
    public void mergeStarted(SortState sortState) {
        view.mergeStarted(sortState);
    }

    @Override
    public void resetCalled() {
        view.resetCalled();
    }
}