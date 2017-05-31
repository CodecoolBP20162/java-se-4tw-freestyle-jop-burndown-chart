package com.codecool.jopburndown.model;

import javax.persistence.*;
import java.util.List;
import java.util.Random;

@Entity
@Table(name = "board")
public class Board {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    @Column(name="board_size")
    private int boardSize;
    @Column(name="score" )
    private int score;
    @OneToOne
    @JoinColumn(name="user_id")
    private User username;

    private char[][] actualBoard;

    public Board(int boardSize, int score, User username) {
        this.boardSize = boardSize;
        this.score = score;
        this.username = username;
    }

    public Board(int size) {
        this.actualBoard = createBoard(size);
        replacer();
    }

    private char[][] createBoard(int size) {
        char[][] board = new char[size][size];
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {

                Random randomNum = new Random();
                int actualRandomNum = randomNum.nextInt(10);

                if (actualRandomNum > 8) {
                    board[i][j] = '*';
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
                if (this.actualBoard[i][j] == '*') {
                    for (int row = i - 1; row <= i + 1; row++) {
                        for (int col = j - 1; col <= j + 1; col++) {
                            if (row >= 0 && col >= 0 && row < this.actualBoard.length && col < this.actualBoard[i].length && this.actualBoard[row][col] != '*') {
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

    public char[][] getActualBoard() {
        return actualBoard;
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
