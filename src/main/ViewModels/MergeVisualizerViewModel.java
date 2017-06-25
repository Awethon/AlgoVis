/**
 * Created by alexthor on 25.06.17.
 */

public class MergeVisualizerViewModel implements IMediator {

    ISequenceGenerator generator;
    ISortPerformer sortPerformer;
    StateSaverModel model;
    AbstractVisualizerModel visualizerModel;

    private boolean startButtonEnabled = false;
    private boolean pauseButtonEnabled = false;
    private boolean abortButtonEnabled = false;
    private boolean previousButtonEnabled = false;
    private boolean nextButtonEnabled = false;
    private boolean generateButtonEnabled = false;

    private int sequenceLength = 0;
    private String generationMode;

    private int[] sequence;

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

    public void generateSequence() {

        this.sequence = generator.generate(generationMode);
        sortPerformer.setSequence(this.sequence);
        model = sortPerformer.performSort();
        startButtonEnabled = true;
        nextButtonEnabled = true;
    }

    public void setGenerationMode(String generationMode) {

        if(generationMode != "Random" && generationMode != "Almost sorted" && generationMode != "Reversed") {
            generateButtonEnabled = false;
            this.generationMode = generationMode;
            return;
        }

        generateButtonEnabled = true;
        this.generationMode = generationMode;
    }

    public void start() {
        startButtonEnabled = false;
        pauseButtonEnabled = true;
        nextButtonEnabled = false;
        previousButtonEnabled = false;
        abortButtonEnabled = true;
    }

    public void pause() {
        pauseButtonEnabled = false;
        startButtonEnabled = true;
        nextButtonEnabled = true;
        previousButtonEnabled = true;

    }

    public void nextStep() {
        abortButtonEnabled = true;
        previousButtonEnabled = true;

    }

    public void previousStep() {

    }

    public void abort() {
        abortButtonEnabled = false;
        pauseButtonEnabled = false;
        previousButtonEnabled = false;
        startButtonEnabled = true;
        nextButtonEnabled = true;
    }

    @Override
    public void AcceptChanges(int newIndex, int oldIndex) {

    }
}
