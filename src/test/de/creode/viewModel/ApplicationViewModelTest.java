package de.creode.viewModel;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ApplicationViewModelTest {

    ApplicationViewModel applicationViewModel;
    ApplicationStateListener appListener;
    @BeforeEach
    void setUp() {
        applicationViewModel = new ApplicationViewModel();
        appListener = new ApplicationStateListener();
        applicationViewModel.getProperty().listen(appListener);
    }

    @Test
    public void notifyAppListeners_afterStateChangedTheStateChangedToTrue(){
        applicationViewModel.getProperty().set(ApplicationState.SIMULATING);
        assertEquals(ApplicationState.SIMULATING, appListener.state);
    }

    @Test
    public void notifyAppListeners_afterSameStateChangedStateIsFalse(){
        applicationViewModel.getProperty().set(ApplicationState.EDITING);
        assertEquals(ApplicationState.EDITING, appListener.state);
    }

   private static class ApplicationStateListener implements ISimpleChangeListener<ApplicationState> {
        public ApplicationState state = ApplicationState.EDITING;
        @Override
        public void valueChanged(ApplicationState newState) {
            this.state= newState;
        }
   }

}