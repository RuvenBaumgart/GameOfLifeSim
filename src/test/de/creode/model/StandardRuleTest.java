package de.creode.model;

import org.junit.jupiter.api.BeforeEach;

import static org.junit.jupiter.api.Assertions.*;

class StandardRuleTest {

    private Board board;
    private Rule simulationRule;

    @BeforeEach
    void setUp(){
        this.board = new BoundedBoard(5,4);
        this.simulationRule = new StandardRule();
    }
}