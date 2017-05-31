package com.codecool.jopburndown.controller;

import com.codecool.jopburndown.model.Board;
import org.json.simple.JSONObject;
import spark.ModelAndView;
import spark.Request;
import spark.Response;

import java.util.HashMap;
import java.util.Map;

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
        int x = Integer.parseInt(req.queryParams("x"));
        int y = Integer.parseInt(req.queryParams("y"));
        JSONObject jsonObj = new JSONObject();
        jsonObj.put("currentChar", board.getActualElement(x, y));
        res.type("application/json");
        return jsonObj;
    }
}
