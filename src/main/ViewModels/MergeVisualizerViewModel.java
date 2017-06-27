
public class MergeVisualizerViewModel implements IMediator {

    //Генератор исходных данных
    ISequenceGenerator generator;
    //Производит сортировку и сохраняет шаги в объект StateSaverModel
    ISortPerformer sortPerformer;
    //Содержит шаги сортировки
    StateSaverModel model;
    //Производит пошаговое прохождение сортировки и уведомляет о изменениях
    private AbstractVisualizerModel visualizerModel;
    //Производит принятие обновленных данных
    IMediator view;

    private boolean startButtonEnabled = false;
    private boolean pauseButtonEnabled = false;
    private boolean abortButtonEnabled = false;
    private boolean previousButtonEnabled = false;
    private boolean nextButtonEnabled = false;
    private boolean generateButtonEnabled = false;
    private boolean customFieldEnabled = false;
    private boolean startButtonWasClicked = false;
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

    public void setVisualizerModel(AbstractVisualizerModel visualizerModel) {
        this.visualizerModel = visualizerModel;
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
            sequenceLength = 0;
            generateButtonEnabled = false;
            return;
        }
        if (input.length() > 9 || !input.matches("\\d+$")) {
            generateButtonEnabled = false;
            sequenceLength = 0;
            return;
        }
        Integer intInput = Integer.parseInt(input);

        if (intInput <= 0 || intInput > 250) {
            generateButtonEnabled = false;
            sequenceLength = 0;
            return;
        }
        sequenceLength = intInput;
        generateButtonEnabled = true;
    }
    //Вызывается по нажатию клавиши generate
    public void generateSequence() {
        this.sequence = generator.generate(generationMode, sequenceLength);
        sortPerformer.setSequence(this.sequence);
        model = sortPerformer.performSort();
        setVisualizerModel(new MergeVisualizerModel(this));
        //visualizerModel.setSortStates(model);
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
        if(sequenceLength != 0)
            generateButtonEnabled = true;
        lengthFieldEnabled = true;
        //this.generationMode = generationMode;
    }
    //Вызывается при нажатии startVisualize
    public void start() {
        startButtonEnabled = false;
        pauseButtonEnabled = true;
        nextButtonEnabled = false;
        previousButtonEnabled = false;
        abortButtonEnabled = true;
        generateButtonEnabled = false;
        lengthFieldEnabled = false;
        if(startButtonWasClicked)
            visualizerModel.continueProcess();
        else {
            startButtonWasClicked = true;
            setVisualizerModel(new MergeVisualizerModel(this));
            visualizerModel.setSortStates(model);
            visualizerModel.start();
        }
    }
    //Вызывается при нажатии pause
    public void pause() {
        pauseButtonEnabled = false;
        startButtonEnabled = true;
        nextButtonEnabled = true;
        previousButtonEnabled = true;
        visualizerModel.pause();
    }

    /**
     * Sounds good, doesn't work. Нужно разобраться с прохождением по шагам.
     */
    public void nextStep() {
        abortButtonEnabled = true;
        previousButtonEnabled = true;
        generateButtonEnabled = false;
        lengthFieldEnabled = false;
        if(!startButtonWasClicked){
            startButtonWasClicked = true;
            setVisualizerModel(new MergeVisualizerModel(this));
            visualizerModel.setSortStates(model);
            visualizerModel.pause();
            visualizerModel.start();
        }
        visualizerModel.nextStep();
        if(visualizerModel.getCurrentState() == model.size()) {
            nextButtonEnabled = false;
            startButtonEnabled = false;
        }
    }
    //Вызывается при нажатии previous
    public void previousStep() {
        visualizerModel.previousStep();
        if(visualizerModel.getCurrentState() == 0)
            previousButtonEnabled = false;
    }
    //Вызывается при нажатии abort
    public void abort() {
        abortButtonEnabled = false;
        pauseButtonEnabled = false;
        previousButtonEnabled = false;
        startButtonEnabled = true;
        nextButtonEnabled = true;
        lengthFieldEnabled = true;
        if(visualizerModel.isAlive())
            visualizerModel.abort();
        else
            resetCalled();
        startButtonWasClicked = false;
        generateButtonEnabled = true;
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
