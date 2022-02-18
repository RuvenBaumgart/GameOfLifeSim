package de.creode.viewModel;

import de.creode.model.Board;

import de.creode.utilities.Property;


public class BoardViewModel {

    private final Property<Board> boardProperty = new Property<>();

    public BoardViewModel(){
    }

    public Property<Board> getBoardProperty() {
        return boardProperty;
    }
}
