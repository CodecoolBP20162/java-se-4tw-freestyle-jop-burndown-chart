package com.codecool.jopburndown.controller;

import com.codecool.jopburndown.model.Board;
import org.json.simple.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import spark.ModelAndView;
import spark.Request;
import spark.Response;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.*;

/**
 * BoardController is responsible for maintaining connection beetween the client and the server
 * within a game. Every communication between user and the actual board instance is done here.
 */
public class BoardController {
    private static final Logger logger = LoggerFactory.getLogger(BoardController.class);
    private static Board board;

    /**
     * This method generates a new board with the information it gets from the client (i.e.
     * how many fields and rows the user wants to be generated). It then redirects to the route
     * "/board", where the instantiated board will be displayed.
     * @param req the Request object
     * @param res the Response object
     * @return a return needed by Spark.
     */
    public static Response createNewBoard(Request req, Response res){
        int size = Integer.parseInt(req.queryParams("size"));
        board = new Board(size);
        res.redirect("/board");
        logger.info("Table creation executed.\nactual board:\n{}", board);

        return res;
    }

    /**
     * This method is responsible for rendering the board page with the actual content
     * of the instantiated board.
     * @param req
     * @param res
     * @return ModelAndView
     */
    public static ModelAndView showBoard(Request req, Response res){
        char[][] actualBoard = board.getActualBoard();
        Map<String, Object> params = new HashMap<>();
        params.put("board", actualBoard);
        req.session().attribute("time",new Date());
        return new ModelAndView(params, "board");
    }

    /**
     * This method will be called from an AJAX call from our javaScript. The Request object
     * contains the x, y coordinates of the field, which the user has clicked. This method is
     * responsible for returning every coordinate and the values on that coordinate which
     * should be revealed to the user.
     * @param req
     * @param res
     * @return JSONObject
     */
    public static JSONObject infoAboutSquare(Request req, Response res){
        JSONObject jsonObj = new JSONObject();
        List<Character> currentChars = new ArrayList<>();
        List<List<Integer>> coordsOfCharsToDisplay;

        int x = Integer.parseInt(req.queryParams("x"));
        int y = Integer.parseInt(req.queryParams("y"));
        char actualChar = board.getActualElement(x, y);

        if (actualChar == '0'){
            coordsOfCharsToDisplay = board.getListToReveal(x, y);
            currentChars = board.getAllCharsToDisplay(coordsOfCharsToDisplay);
        } else {
            coordsOfCharsToDisplay = Arrays.asList(Arrays.asList(x, y));
            currentChars.add(actualChar);
        }

        jsonObj.put("currentChars", currentChars);
        jsonObj.put("coords", coordsOfCharsToDisplay);
        res.type("application/json");
        return jsonObj;
    }

    /**
     * This method will return a jsonObj containing the number of all mines present in the
     * board.
     * @param req
     * @param res
     * @return JSONObject
     */
    public static JSONObject countMines(Request req, Response res){
        JSONObject jsonObj = new JSONObject();
        jsonObj.put("numberOfMines", board.mineCounter);
        res.type("application/json");
        return jsonObj;
    }
}
