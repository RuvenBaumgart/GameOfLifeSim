package de.creode.model;

import de.creode.model.board.Board;
import de.creode.model.board.BoundedBoard;
import org.junit.jupiter.api.BeforeEach;

class StandardRuleTest {

    private Board board;
    private Rule simulationRule;

    @BeforeEach
    void setUp(){
        this.board = new BoundedBoard(5,4);
        this.simulationRule = new StandardRule();
    }
}