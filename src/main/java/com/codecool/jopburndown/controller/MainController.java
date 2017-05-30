package com.codecool.jopburndown.controller;


import com.codecool.jopburndown.model.Board;
import spark.ModelAndView;
import spark.Request;
import spark.Response;

import java.util.HashMap;

public class MainController {

    public static ModelAndView renderIndex(Request req, Response res) {
        HashMap<String, String> map = new HashMap<>();
        return new ModelAndView(map,"form");
    }
}
