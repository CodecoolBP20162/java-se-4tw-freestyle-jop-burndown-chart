package com.codecool.jopburndown.model;

import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

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

    @Test
    void testGetListToReveal_GivesBackValidCoordinates(){
        Board board = new Board(3);
        char[][] charArr = {{'0','1','9'},{'0','1','1'},{'0','0','0'}};
        board.setActualBoard(charArr);
        board.setCopyBoard(charArr);

        List<List<Integer>> coordsOfCharsToDisplay = new ArrayList<List<Integer>>();
        coordsOfCharsToDisplay.add(Arrays.asList(0, 0));
        coordsOfCharsToDisplay.add(Arrays.asList(0, 1));
        coordsOfCharsToDisplay.add(Arrays.asList(1, 0));
        coordsOfCharsToDisplay.add(Arrays.asList(1, 1));
        coordsOfCharsToDisplay.add(Arrays.asList(2, 0));
        coordsOfCharsToDisplay.add(Arrays.asList(2, 1));
        coordsOfCharsToDisplay.add(Arrays.asList(1, 2));
        coordsOfCharsToDisplay.add(Arrays.asList(2, 2));

        assertEquals(coordsOfCharsToDisplay, board.getListToReveal(0,0));
    }

    @Test
    void testGetAllCharsToDisplay_GivesBackValidValues(){

        Board board = new Board(3);
        char[][] charArr = {{'0','1','9'},{'0','1','1'},{'0','0','0'}};
        board.setActualBoard(charArr);

        List<List<Integer>> coordsOfCharsToDisplay = new ArrayList<List<Integer>>();
        coordsOfCharsToDisplay.add(Arrays.asList(0, 0));
        coordsOfCharsToDisplay.add(Arrays.asList(0, 1));
        coordsOfCharsToDisplay.add(Arrays.asList(0, 2));

        assertEquals(Arrays.asList('0', '1', '9'), board.getAllCharsToDisplay(coordsOfCharsToDisplay));
    }
}