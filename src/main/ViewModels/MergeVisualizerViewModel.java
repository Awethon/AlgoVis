/**
 * Created by alexthor on 25.06.17.
 */

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

    private int sequenceLength = 0;
    private String generationMode;

    private int[] sequence;

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
    //Вызывается при изменении поля с длиной последовательности
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
        if(!generationMode.equals("Random")
                && !generationMode.equals("Almost sorted")
                && !generationMode.equals ("Reversed")) {
            generateButtonEnabled = false;
            //this.generationMode = generationMode;
            return;
        }

        generateButtonEnabled = true;
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
    public void mergePerformed(State state) {
        view.mergePerformed(state);
    }

    @Override
    public void mergeStarted(State state) {
        view.mergeStarted(state);
    }
}
