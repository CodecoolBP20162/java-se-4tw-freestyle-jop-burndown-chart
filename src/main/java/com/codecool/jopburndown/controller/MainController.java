package com.codecool.jopburndown.controller;

import spark.ModelAndView;
import spark.Request;
import spark.Response;

import java.util.HashMap;

public class MainController {

    public static ModelAndView renderDifficultyForm(Request req, Response res) {
        if(!req.session().attributes().contains("user")) {
            return UserController.renderLogin(req, res);
        }
        HashMap<String, String> map = new HashMap<>();
        return new ModelAndView(map,"form");
    }


    public static ModelAndView renderIndexWithUser(Request req, Response res) {
        HashMap<String, String> map = new HashMap<>();
        map.put("username", req.session().attribute("username"));
        return new ModelAndView(map, "index");
    }

}
