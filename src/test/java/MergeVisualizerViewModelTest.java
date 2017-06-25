import org.junit.Test;

import static junit.framework.TestCase.assertFalse;
import static junit.framework.TestCase.assertTrue;

/**
 * Created by alexthor on 25.06.17.
 */

public class MergeVisualizerViewModelTest {
    @Test
    public void startButtonDisabledByDefault(){
        MergeVisualizerViewModel viewModel = new MergeVisualizerViewModel();
        assertFalse(viewModel.isStartButtonEnabled());
    }
    @Test
    public void pauseButtonDisabledByDefault(){
        MergeVisualizerViewModel viewModel = new MergeVisualizerViewModel();
        assertFalse(viewModel.isPauseButtonEnabled());
    }
    @Test
    public void nextButtonDisabledByDefault(){
        MergeVisualizerViewModel viewModel = new MergeVisualizerViewModel();
        assertFalse(viewModel.isNextButtonEnabled());
    }
    @Test
    public void previousButtonDisabledByDefault(){
        MergeVisualizerViewModel viewModel = new MergeVisualizerViewModel();
        assertFalse(viewModel.isPreviousButtonEnabled());
    }
    @Test
    public void abortButtonDisabledByDefault(){
        MergeVisualizerViewModel viewModel = new MergeVisualizerViewModel();
        assertFalse(viewModel.isAbortButtonEnabled());
    }

    @Test
    public void generateButtonDisabledByDefault(){
        MergeVisualizerViewModel viewModel = new MergeVisualizerViewModel();
        assertFalse(viewModel.isGenerateButtonEnabled());
    }

    @Test
    public void generateButtonEnabledWhenEnterNumber(){
        MergeVisualizerViewModel viewModel = new MergeVisualizerViewModel();
        viewModel.setSequenceLength("1");
        assertTrue(viewModel.isGenerateButtonEnabled());
    }

    @Test
    public void generateButtonDisabledWhenEnterInvalidNumber(){
        MergeVisualizerViewModel viewModel = new MergeVisualizerViewModel();
        viewModel.setSequenceLength("-1");
        assertFalse(viewModel.isGenerateButtonEnabled());
    }

    @Test
    public void generateButtonDisabledWhenClearNumber(){
        MergeVisualizerViewModel viewModel = new MergeVisualizerViewModel();
        viewModel.setSequenceLength("");
        assertFalse(viewModel.isGenerateButtonEnabled());
    }

    @Test
    public void generateButtonDisabledWhenCustomEnabled(){
        MergeVisualizerViewModel viewModel = new MergeVisualizerViewModel();
        viewModel.setSequenceLength("5");
        viewModel.setGenerationMode("Custom");
        assertFalse(viewModel.isGenerateButtonEnabled());
    }

    @Test
    public void startButtonEnabledWhenGenerateClicked(){
        MergeVisualizerViewModel viewModel = new MergeVisualizerViewModel();
        viewModel.setGenerator(new SequenceGenerator());
        viewModel.setSortPerformer(new MergeSortPerformerModel());
        viewModel.setSequenceLength("4");
        viewModel.generateSequence();
        assertTrue(viewModel.isStartButtonEnabled());
    }

    @Test
    public void ButtonsStatesWhenStartClicked(){
        MergeVisualizerViewModel viewModel = new MergeVisualizerViewModel();
        viewModel.setGenerator(new SequenceGenerator());
        viewModel.setSortPerformer(new MergeSortPerformerModel());
        viewModel.setSequenceLength("4");
        viewModel.generateSequence();
        viewModel.start();
        assertFalse(viewModel.isStartButtonEnabled());
        assertFalse(viewModel.isNextButtonEnabled());
        assertFalse(viewModel.isPreviousButtonEnabled());
        assertTrue(viewModel.isPauseButtonEnabled());
        assertTrue(viewModel.isAbortButtonEnabled());
    }

    @Test
    public void ButtonsStatesWhenPauseClicked(){
        MergeVisualizerViewModel viewModel = new MergeVisualizerViewModel();
        viewModel.setGenerator(new SequenceGenerator());
        viewModel.setSortPerformer(new MergeSortPerformerModel());
        viewModel.setSequenceLength("4");
        viewModel.generateSequence();
        viewModel.start();
        viewModel.pause();
        assertTrue(viewModel.isStartButtonEnabled());
        assertFalse(viewModel.isPauseButtonEnabled());
        assertTrue(viewModel.isPreviousButtonEnabled());
        assertTrue(viewModel.isNextButtonEnabled());
        assertTrue(viewModel.isAbortButtonEnabled());
    }

    @Test
    public void ButtonsStatesWhenNextClicked(){
        MergeVisualizerViewModel viewModel = new MergeVisualizerViewModel();
        viewModel.setGenerator(new SequenceGenerator());
        viewModel.setSortPerformer(new MergeSortPerformerModel());
        viewModel.setSequenceLength("4");
        viewModel.generateSequence();
        viewModel.nextStep();
        assertTrue(viewModel.isStartButtonEnabled());
        assertTrue(viewModel.isNextButtonEnabled());
        assertTrue(viewModel.isPreviousButtonEnabled());
        assertTrue(viewModel.isAbortButtonEnabled());
        assertFalse(viewModel.isPauseButtonEnabled());
    }

    @Test
    public void ButtonsStatesWhenPreviousClicked(){
        MergeVisualizerViewModel viewModel = new MergeVisualizerViewModel();
        viewModel.setGenerator(new SequenceGenerator());
        viewModel.setSortPerformer(new MergeSortPerformerModel());
        viewModel.setSequenceLength("4");
        viewModel.generateSequence();
        viewModel.start();
        viewModel.pause();
        viewModel.previousStep();
        assertTrue(viewModel.isStartButtonEnabled());
        assertTrue(viewModel.isNextButtonEnabled());
        assertTrue(viewModel.isPreviousButtonEnabled());
        assertTrue(viewModel.isAbortButtonEnabled());
        assertFalse(viewModel.isPauseButtonEnabled());
    }

    @Test
    public void ButtonsStatesWhenAbortClicked(){
        MergeVisualizerViewModel viewModel = new MergeVisualizerViewModel();
        viewModel.setGenerator(new SequenceGenerator());
        viewModel.setSortPerformer(new MergeSortPerformerModel());
        viewModel.setSequenceLength("4");
        viewModel.generateSequence();
        viewModel.start();
        viewModel.abort();
        assertTrue(viewModel.isStartButtonEnabled());
        assertTrue(viewModel.isNextButtonEnabled());
        assertFalse(viewModel.isPreviousButtonEnabled());
        assertFalse(viewModel.isAbortButtonEnabled());
        assertFalse(viewModel.isPauseButtonEnabled());
    }


}
