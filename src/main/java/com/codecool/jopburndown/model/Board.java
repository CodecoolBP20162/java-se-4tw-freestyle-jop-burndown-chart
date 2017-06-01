package com.codecool.jopburndown.model;

import javax.persistence.*;
import java.util.List;
import java.util.Random;
import com.codecool.jopburndown.controller.BoardController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.*;

/**
 * An instance of a Board represents one playing board of a minesweeper game.
 * Upon instantiating, it creates an actual board and a copy of that board. By calling
 * the sout method on a board instance, its toString method will print out the whole
 * playing board in a formatted form.
 */
@Entity
@Table(name = "board")
public class Board {
    private static final Logger logger = LoggerFactory.getLogger(Board.class);

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
    private char[][] copyBoard;
    @Transient
    private List<List<Integer>> coords = new ArrayList<List<Integer>>();
    public int mineCounter = 0;

    public Board(int boardSize, int score, User username) {
        this.boardSize = boardSize;
        this.score = score;
        this.username = username;
    }

    /**
     * The constructor creates an actual board. This is done by the createBoard method.
     * @see Board#createBoard(int)
     * Then it calls the replacer method. To see how this works
     * @see Board#replacer()
     * It also creates a copy of that field, which will be used in the search engine.
     * @see Board#searchEngine(int, int)
     * @param size
     */
    public Board(int size) {
        actualBoard = createBoard(size);
        replacer();
        copyBoard = deepCopyBoard();
        logger.debug("Board deep copied! actual board: {} copied board: {}", actualBoard.hashCode(), copyBoard.hashCode());
        logger.info("Actual number of mines: {}", mineCounter);

    }

    /**
     * The createBoard method creates a 2D char array. Both the inner arrays and the outer
     * array will be "size" length. The method will place two kind of characters into the
     * board - zeros and nines. Nine will represent a bomb or mine, and zero represents
     * a safe field. After the board is filled with numbers, the method will give bach
     * the 2D array.
     * @param size This parameter will be used to create the size of the arrays.
     * @return 2D char array, which represents the board with all the bombs and safe areas.
     */
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
        logger.info("New board created. - actual size: {}", size);
        logger.debug("Actual size: {}", size);

        return board;
    }

    /**
     * The replacer method iterates through the 2D char array of our board and
     * will replace the fields next to a bomb or mine to the number of how many bombs
     * are next to this certain field.
     */
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

    /**
     * This method is a self-made deepcopy method which will copy the actualBoard 2D array.
     * @return the new 2D array, which is an exact copy of the original actualBoard.
     */
    private char[][] deepCopyBoard(){
        char[][] current = new char[this.actualBoard.length][this.actualBoard.length];
        for(int i=0; i<this.actualBoard.length; i++)
            for(int j=0; j<this.actualBoard[i].length; j++)
                current[i][j] = this.actualBoard[i][j];
        return current;
    }

    /**
     * Getter for the actualBoard
     * @return the actualBoard
     */
    public char[][] getActualBoard() {
        return actualBoard;
    }

    /**
     * Returns a given fields content by its coordinates.
     * @param x the x coordinate of the field
     * @param y the y coordinate of the field
     * @return the value of the given field
     */
    public char getActualElement(int x, int y){
        return this.actualBoard[x][y];
    }

    /**
     * This is the function which will be called from outside the Board class in order
     * to return all coordinates where there is a zero or a field next to a zero.
     * @param x the x coordinate of the center of the examination
     * @param y the y coordinate of the center of the examination
     * @return all the coordinates where there is a zero or a number next to a zero.
     */
    public List<List<Integer>> getListToReveal(int x, int y){
        this.coords.clear();
        searchEngine(x, y);
        return this.coords;
    }

    /**
     * This recursive method will update the instance variable "coords", which is a list of lists.
     * Coords list contains lists of coordinates (two integers). Every time this method is called
     * it will update the list with the current coordinates, and check for the eight neighboring fields
     * if there is a zero or a number there. If there is a zero, it will turn the current 0 into an X.
     * This is important in order to mark the elements which are already in the coordinates list - if
     * the engine finds an X it will not examine this field anymore.
     * Afther this, it again calls this method and do the search and update the coords again.
     * If it finds a number, it marks this number as an X and it will return true and so the recursion
     * stops on that level, and it goes back to the level before.
     * @param x x coordinate of the center of the examination
     * @param y y coordinate of the center of the examination
     * @return true in order to exit the current level of the recursion.
     */
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

    /**
     * This method will get ceratin coordinates, it checks what numbers are present at these locations
     * and will return a list of these elements.
     * @param coords A list of coordinates
     * @return A list of characters, which are present at the given coordinates.
     */
    public List<Character> getAllCharsToDisplay(List<List<Integer>> coords){
        List<Character> chars = new ArrayList<>();
        for (List<Integer> coord: coords){
            chars.add(this.actualBoard[coord.get(0)][coord.get(1)]);
        }
        return chars;
    }

    /**
     * The overriden toString method will print out the currentBoard of the instance in a human
     * readable nice form.
     * @return returns the string to be printed.
     */
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
