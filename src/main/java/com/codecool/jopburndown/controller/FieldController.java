package com.codecool.jopburndown.controller;

import com.codecool.jopburndown.model.Board;
import org.json.simple.JSONObject;
import spark.ModelAndView;
import spark.Request;
import spark.Response;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.*;

public class FieldController {

    private static Board board;

    public static Response createNewBoard(Request req, Response res){
        int size = Integer.parseInt(req.queryParams("size"));
        board = new Board(size);
        res.redirect("/board");
        return res;
    }

    public static ModelAndView showBoard(Request req, Response res){
        char[][] actualBoard = board.getActualBoard();
        Map<String, Object> params = new HashMap<>();
        params.put("board", actualBoard);
        return new ModelAndView(params, "board");
    }

    public static JSONObject infoAboutSquare(Request req, Response res){

        System.out.println(board);

        JSONObject jsonObj = new JSONObject();
        int x = Integer.parseInt(req.queryParams("x"));
        int y = Integer.parseInt(req.queryParams("y"));
        char actualChar = board.getActualElement(x, y);
        List<Character> currentChars = new ArrayList<>();
        List<List<Integer>> coordsOfCharsToDisplay;

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



    public static JSONObject countMines(Request req, Response res){
        JSONObject jsonObj = new JSONObject();
        jsonObj.put("numberOfMines", board.mineCounter);
        res.type("application/json");
        return jsonObj;
    }
}
