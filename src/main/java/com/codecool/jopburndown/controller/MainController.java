package com.codecool.jopburndown.controller;

import com.codecool.jopburndown.model.Board;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import spark.ModelAndView;
import spark.Request;
import spark.Response;
import java.util.HashMap;

/**
 * MainController class is responsible for rendering the main page, which is a form, where
 * one can choose the difficulty level.
 */
public class MainController {

    public static ModelAndView renderIndex(Request req, Response res) {
        if (!req.session().attributes().contains("user")) {
            return UserController.renderLogin(req, res);
        }
        HashMap<String, String> map = new HashMap<>();
        return new ModelAndView(map,"form");
    }

    /**
     * This method renders the main page. It will return a Spark ModelAndView object.
     * @param req
     * @param res
     * @return
     */
    public static ModelAndView renderDifficultyForm(Request req, Response res) {
        HashMap<String, String> map = new HashMap<>();
        return new ModelAndView(map,"form");
    }

    public static ModelAndView renderIndexWithUser(Request req, Response res) {
        HashMap<String, String> map = new HashMap<>();
        map.put("username", req.session().attribute("username"));
        return new ModelAndView(map, "index");
    }
}
