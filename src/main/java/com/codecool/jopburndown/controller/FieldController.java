package com.codecool.jopburndown.controller;


import com.codecool.jopburndown.model.Board;
import spark.Request;
import spark.Response;

public class FieldController {

    public static Response createNewBoard(Request req, Response res){
        int size = Integer.parseInt(req.queryParams("size"));
        Board board = new Board(size);
        System.out.println(board);
        return res;
    }
}
