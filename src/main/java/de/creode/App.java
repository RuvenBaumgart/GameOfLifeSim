package de.creode;

import de.creode.View.Infobar;
import de.creode.View.MainView;
import de.creode.View.SimulationView;
import de.creode.View.Toolbar;
import de.creode.model.BoundedBoard;
import de.creode.utilities.event.EventBus;
import de.creode.utilities.event.MyMouseEvent;
import de.creode.utilities.event.ToolBarEvent;
import de.creode.viewModel.*;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class App extends Application{

    private static final int B_HEIGHT = 60;
    private static final int B_WIDTH = 60;

    @Override
    public void start(Stage stage) {
        EventBus eventBus = new EventBus();

        ApplicationViewModel applicationViewModel = new ApplicationViewModel();
        BoardViewModel boardViewModel = new BoardViewModel();
        BoundedBoard boundedBoard = new BoundedBoard(B_HEIGHT, B_WIDTH);
        boardViewModel.getBoardProperty().set(boundedBoard);

        EditorViewModel editorViewModel = new EditorViewModel(boardViewModel);
        SimulationViewModel simulationViewModel = new SimulationViewModel(boardViewModel, applicationViewModel);
        applicationViewModel.getProperty().listen(editorViewModel::onAppStateChanged);

        Infobar infobar = new Infobar();
        Toolbar toolbar = new Toolbar(eventBus);
        SimulationView simulationView = new SimulationView(boardViewModel, eventBus);


        boardViewModel.getBoardProperty().listen(simulationView::draw);

        eventBus.listen(ToolBarEvent.class, simulationViewModel::handle);
        eventBus.listen(ToolBarEvent.class, editorViewModel::handle);

        editorViewModel.getCellStateProperty().listen(infobar::displayMode);
        editorViewModel.getCursorPositionProperty().listen(infobar::setCursorPosFormat);
        editorViewModel.getCursorPositionProperty().listen(cellposition->{
            simulationView.draw(boardViewModel.getBoardProperty().get());
        });

        editorViewModel.getCursorPositionProperty().listen(simulationView::drawHighlight);

        eventBus.listen(MyMouseEvent.class, editorViewModel::handle);

        MainView mainView = new MainView();
        mainView.setPadding(new Insets(3.0));
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