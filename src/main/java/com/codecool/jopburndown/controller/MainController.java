package com.codecool.jopburndown.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import spark.ModelAndView;
import spark.Request;
import spark.Response;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

/**
 * MainController class is responsible for rendering the main page, which is a form, where
 * one can choose the difficulty level.
 */
public class MainController {

    private static final Logger logger = LoggerFactory.getLogger(MainController.class);

    public static ModelAndView renderIndex(Request req, Response res) {
        if (!req.session().attributes().contains("user")) {
            return UserController.renderLogin(req, res);
        }
        HashMap<String, String> map = new HashMap<>();
        logger.info("Index page access");
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

    public static Response getWinningTime( Request req ,Response response){
        if(req.session().attributes().contains("time")){
            Date oldTime = req.session().attribute("time");
            long milSec = new Date().getTime() - oldTime.getTime();
            Date date = new Date(milSec);
            SimpleDateFormat dateFormat = new SimpleDateFormat("mm-ss-SS");
            String format =  dateFormat.format(date);
            req.session().removeAttribute("time");
        }
        return response;
    }
}
