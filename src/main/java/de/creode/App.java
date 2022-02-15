package de.creode;

import de.creode.viewModel.ApplicationState;
import de.creode.viewModel.ApplicationViewModel;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class App extends Application {

    @Override
    public void start(Stage stage) {
        ApplicationViewModel applicationViewModel = new ApplicationViewModel(ApplicationState.EDITING);
        MainView mainView = new MainView(applicationViewModel);
        Scene scene = new Scene(mainView, 740, 660);
        stage.setScene(scene);
        stage.show();
        mainView.draw();
    }


    public static void main(String[] args) {
        launch();
    }

}