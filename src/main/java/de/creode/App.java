/*
 * This App is developed by Ruven Baumgart www.creode.de.
 *
 * The Idea of creating this app and make use of various programming techniques like TDD and VVMM
 * was based on Robert C. Martins book "The Clean Coder" where I have reading about Conways Game of Life
 * https://en.wikipedia.org/wiki/Conway%27s_Game_of_Life was used as a Coding-Kata.
 * Byte Smith Video Series was also a great resource of inspiration and knowledge how to code Conways game of life.
 *
 *
 */

package de.creode;

import de.creode.View.*;
import de.creode.model.board.BoardDrawLayer;
import de.creode.model.board.BoundedBoard;
import de.creode.model.board.GridDrawLayer;
import de.creode.model.board.HighlightDrawLayer;
import de.creode.utilities.event.EventBus;
import de.creode.utilities.event.MyMouseEvent;
import de.creode.utilities.event.OptionsEvent;
import de.creode.utilities.event.ToolBarEvent;
import de.creode.viewModel.*;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;

import javafx.stage.Stage;

public class App extends Application{

    private static final int B_HEIGHT = 25;
    private static final int B_WIDTH = 25;

    @Override
    public void start(Stage stage) {
        /*
         * Initialization of all components.
         * The concept of application context can be used but for now i will leave the init functions here
         */
        EventBus eventBus = new EventBus();
        ApplicationViewModel applicationViewModel = new ApplicationViewModel();
        BoardViewModel boardViewModel = new BoardViewModel();
        BoundedBoard boundedBoard = new BoundedBoard(B_HEIGHT, B_WIDTH);
        boardViewModel.getBoardProperty().set(boundedBoard);
        EditorViewModel editorViewModel = new EditorViewModel(boardViewModel);
        SimulationViewModel simulationViewModel = new SimulationViewModel(boardViewModel, applicationViewModel);
        Infobar infobar = new Infobar();
        Toolbar toolbar = new Toolbar(eventBus);
        BoardDrawLayer boardDrawLayer = new BoardDrawLayer(boardViewModel);
        GridDrawLayer gridDrawLayer = new GridDrawLayer(boardViewModel);
        HighlightDrawLayer highlightDrawLayer = new HighlightDrawLayer(editorViewModel);
        SimulationView simulationView = new SimulationView(eventBus);
        simulationView.addDrawLayer(boardDrawLayer);
        simulationView.addDrawLayer(gridDrawLayer);
        simulationView.addDrawLayer(highlightDrawLayer);
        OptionsView optionsView = new OptionsView(eventBus);

        /*
         * Connecting the Views and the ViewModels
         */

        /*
         * Eventbus listeners
         */
        eventBus.listen(MyMouseEvent.class, editorViewModel::handle);
        eventBus.listen(ToolBarEvent.class, simulationViewModel::handle);
        eventBus.listen(ToolBarEvent.class, editorViewModel::handle);
        eventBus.listen(OptionsEvent.class, boardViewModel::handle);
        eventBus.listen(OptionsEvent.class, editorViewModel::handle);
        eventBus.listen(OptionsEvent.class, simulationViewModel::handle);

        /*
         * Listeners to the change of Properties
         */
        applicationViewModel.getProperty().listen(editorViewModel::onAppStateChanged);
        applicationViewModel.getProperty().listen(infobar::displayState);
        boardViewModel.getBoardProperty().listen(board ->{boardDrawLayer.notifyListener();});
        boardViewModel.getBoardProperty().listen(board -> simulationView.fetchBoardSize(board.getHeigth(), board.getWidth()));
        editorViewModel.getCellStateProperty().listen(infobar::displayMode);
        editorViewModel.getCursorPositionProperty().listen(infobar::setCursorPosFormat);
        editorViewModel.getCellStateProperty().listen(infobar::displayMode);
        editorViewModel.getCursorPositionProperty().listen(infobar::setCursorPosFormat);
        editorViewModel.getCursorPositionProperty().listen(board ->{boardDrawLayer.notifyListener();});

        /*
         * Setting up the Main BorderPane View
         */
        MainView mainView = new MainView();
        mainView.setPadding(new Insets(3.0));
        mainView.setTop(toolbar);
        mainView.setCenter(simulationView);
        mainView.setBottom(infobar);
        mainView.setRight(optionsView);

        Scene scene = new Scene(mainView, 1200, 900);
        String stylesheet = getClass().getResource("/styles.css").toExternalForm();
        scene.getStylesheets().add(stylesheet);

        stage.setScene(scene);
        stage.show();
    }


    public static void main(String[] args) {
        launch();
    }

}