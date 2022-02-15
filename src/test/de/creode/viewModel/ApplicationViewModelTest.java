package de.creode.viewModel;

import de.creode.App;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ApplicationViewModelTest {

    ApplicationViewModel applicationViewModel;
    ApplicationStateListener appListener;
    @BeforeEach
    void setUp() {
        applicationViewModel = new ApplicationViewModel(ApplicationState.EDITING);
        appListener = new ApplicationStateListener();
        applicationViewModel.listenToAppState(appListener);
    }

    @Test
    public void notifyAppListeners_afterStateChangedTheStateChangedToTrue(){
        applicationViewModel.setCurrentState(ApplicationState.SIMULATING);
        assertEquals(ApplicationState.SIMULATING, appListener.state);
    }

    @Test
    public void notifyAppListeners_afterSameStateChangedStateIsFalse(){
        applicationViewModel.setCurrentState(ApplicationState.EDITING);
        assertEquals(ApplicationState.EDITING, appListener.state);
    }

   private static class ApplicationStateListener implements ISimpleStateListener<ApplicationState> {
        public ApplicationState state = ApplicationState.EDITING;
        @Override
        public void valueChanged(ApplicationState newState) {
            this.state= newState;
        }
   }

}