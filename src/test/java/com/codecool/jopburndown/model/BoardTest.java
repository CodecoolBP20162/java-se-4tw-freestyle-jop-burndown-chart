package com.codecool.jopburndown.model;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.*;
import static org.powermock.api.mockito.PowerMockito.when;

class BoardTest {

    @Test
    void testCreateBoard_CreatesValidArrayLength() {
        Board board = new Board(3);
        assertEquals(3, board.getActualBoard().length);
    }

    @Test
    void testCreateBoard_CreatesValidInnerArrayLength() {
        Board board = new Board(3);
        assertEquals(3, board.getActualBoard()[0].length);
    }

    @Test
    void testDeepCopyBoard_CopiesArray() {
        Board board = new Board(3);
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                assertEquals(board.getActualBoard()[i][j], board.getCopyBoard()[i][j]);
            }
        }
    }

    @Test
    void testReplacer_ReplacesFieldsNextToMines() {
        Board board = new Board(10);
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                if (board.getActualBoard()[i][j] == '9' && i - 1 >= 0) {
                    assertNotEquals('0', board.getActualBoard()[i - 1][j]);
                }
            }
        }
    }
}