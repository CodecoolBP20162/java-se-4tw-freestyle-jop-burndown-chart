package com.codecool.jopburndown.model;


import com.sun.org.apache.xpath.internal.SourceTree;

import java.util.*;

public class Board {

    private char[][] actualBoard;
    private char[][] copyBoard;
    private List<List<Integer>> coords = new ArrayList<List<Integer>>();
    public int mineCounter = 0;

    public Board(int size) {
        this.actualBoard = createBoard(size);
        replacer();
        this.copyBoard = deepCopyBoard();
    }

    private char[][] createBoard(int size) {
        char[][] board = new char[size][size];
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {

                Random randomNum = new Random();
                int actualRandomNum = randomNum.nextInt(10);

                if (actualRandomNum > 8) {
                    board[i][j] = '9';
                    mineCounter++;
                } else {
                    board[i][j] = '0';
                }
            }
        }
        return board;
    }

    private void replacer() {
        for (int i = 0; i < this.actualBoard.length; i++) {
            for (int j = 0; j < this.actualBoard[i].length; j++) {
                if (this.actualBoard[i][j] == '9') {
                    for (int row = i - 1; row <= i + 1; row++) {
                        for (int col = j - 1; col <= j + 1; col++) {
                            if (row >= 0 && col >= 0 && row < this.actualBoard.length && col < this.actualBoard[i].length && this.actualBoard[row][col] != '9') {
                                int currentElement = (int) (this.actualBoard[row][col]);
                                currentElement += 1;
                                this.actualBoard[row][col] = (char) currentElement;
                            }
                        }
                    }
                }
            }
        }
    }

    private char[][] deepCopyBoard(){
        char[][] current = new char[this.actualBoard.length][this.actualBoard.length];
        for(int i=0; i<this.actualBoard.length; i++)
            for(int j=0; j<this.actualBoard[i].length; j++)
                current[i][j] = this.actualBoard[i][j];
        return current;
    }

    public char[][] getActualBoard() {
        return actualBoard;
    }

    public char getActualElement(int x, int y){
        return this.actualBoard[x][y];
    }

    public List<List<Integer>> getListToReveal(int x, int y){
        this.coords.clear();
        searchEngine(x, y);
        System.out.println(coords);
        return this.coords;
    }

    private boolean searchEngine(int x, int y){
        this.coords.add(Arrays.asList(x, y));

        if (this.copyBoard[x][y] == '0') {
            this.copyBoard[x][y] = 'X';
            for (int i = x - 1; i <= x + 1; i++) {
                for (int j = y - 1; j <= y + 1; j++) {
                    if (i >= 0 && j >= 0 && i < this.copyBoard.length && j < this.copyBoard.length && this.copyBoard[i][j] != 'X') {
                        searchEngine(i, j);
                    }
                }
            }
        }
        this.copyBoard[x][y] = 'X';
        return true;
    }

    public List<Character> getAllCharsToDisplay(List<List<Integer>> coords){
        List<Character> chars = new ArrayList<>();
        for (List<Integer> coord: coords){
            chars.add(this.actualBoard[coord.get(0)][coord.get(1)]);
        }
        return chars;
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        for (char[] row : this.actualBoard) {
            for (char ch : row) {
                result.append("[" + ch + "]");
            }
            result.append(System.getProperty("line.separator"));
        }
        return result.toString();
    }
}
