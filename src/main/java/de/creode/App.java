package de.creode;

import de.creode.View.Infobar;
import de.creode.View.MainView;
import de.creode.View.SimulationView;
import de.creode.View.Toolbar;
import de.creode.model.BoundedBoard;
import de.creode.viewModel.*;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class App extends Application{

    private static final int B_HEIGHT = 60;
    private static final int B_WIDTH = 60;

    @Override
    public void start(Stage stage) {
        ApplicationViewModel applicationViewModel = new ApplicationViewModel();
        BoardViewModel boardViewModel = new BoardViewModel();
        BoundedBoard boundedBoard = new BoundedBoard(B_HEIGHT, B_WIDTH);
        boardViewModel.getBoardProperty().set(boundedBoard);

        EditorViewModel editorViewModel = new EditorViewModel(boardViewModel, boundedBoard);
        SimulationViewModel simulationViewModel = new SimulationViewModel(boardViewModel);
        applicationViewModel.getProperty().listen(editorViewModel::onAppStateChanged);
        applicationViewModel.getProperty().listen(simulationViewModel::onAppStateChange);

        Infobar infobar = new Infobar(editorViewModel );
        infobar.setCursorPosFormat(0,0);
        Toolbar toolbar = new Toolbar(editorViewModel, applicationViewModel, simulationViewModel );
        SimulationView simulationView = new SimulationView(editorViewModel, boardViewModel);

        MainView mainView = new MainView();
        mainView.setTop(toolbar);
        mainView.setCenter(simulationView);
        mainView.setBottom(infobar);

        Scene scene = new Scene(mainView, 1200, 800);
        stage.setScene(scene);
        stage.show();
        boardViewModel.getBoardProperty().set(boundedBoard);
    }


    public static void main(String[] args) {
        launch();
    }

}