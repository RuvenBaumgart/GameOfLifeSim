package de.creode.viewModel;

import java.util.LinkedList;
import java.util.List;

public class ApplicationViewModel {
    private ApplicationState currentState;
    private List<ISimpleChangeListener<ApplicationState>> appStateListeners;


    public ApplicationViewModel(ApplicationState currentState){
        this.currentState = currentState;
        this.appStateListeners = new LinkedList<>();
    }

    public void listenToAppState(ISimpleChangeListener<ApplicationState> listener){
        this.appStateListeners.add(listener);
    }

    public void setCurrentState(ApplicationState newState){
        if(this.currentState != newState) {
            this.currentState = newState;
            notifyAppStateListeners();
        }
    }

    private void notifyAppStateListeners() {
        for (ISimpleChangeListener appStateListener : appStateListeners) {
            appStateListener.valueChanged(currentState);
        }
    }
}
