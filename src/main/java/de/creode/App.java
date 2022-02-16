package de.creode;

import de.creode.model.BoundedBoard;
import de.creode.viewModel.ApplicationState;
import de.creode.viewModel.ApplicationViewModel;
import de.creode.viewModel.BoardViewModel;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class App extends Application{

    private static final int B_HEIGHT = 60;
    private static final int B_WIDTH = 60;

    @Override
    public void start(Stage stage) {
        ApplicationViewModel applicationViewModel = new ApplicationViewModel(ApplicationState.EDITING);
        BoardViewModel boardViewModel = new BoardViewModel();
        BoundedBoard boundedBoard = new BoundedBoard(B_HEIGHT, B_WIDTH);
        MainView mainView = new MainView(applicationViewModel, boardViewModel, boundedBoard);
        Scene scene = new Scene(mainView, 740, 660);
        stage.setScene(scene);
        stage.show();
       boardViewModel.setBoard(boundedBoard);
    }


    public static void main(String[] args) {
        launch();
    }

}