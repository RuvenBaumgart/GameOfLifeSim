package de.creode.viewModel;

import de.creode.model.board.Board;

import de.creode.model.board.BoundedBoard;
import de.creode.utilities.Property;
import de.creode.utilities.event.OptionsEvent;


public class BoardViewModel {

    private final Property<Board> boardProperty = new Property<>();

    public BoardViewModel(){

      }

    public Property<Board> getBoardProperty() {
        return boardProperty;
    }

    public void handle(OptionsEvent optionsEvent) {
        switch(optionsEvent.getType()){
            case GRID_SIZE:
                boardProperty.set(new BoundedBoard(optionsEvent.getValue(), optionsEvent.getValue()));
                break;
        }
    }
}
