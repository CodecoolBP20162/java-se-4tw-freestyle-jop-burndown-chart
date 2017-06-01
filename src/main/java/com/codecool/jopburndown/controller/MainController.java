package com.codecool.jopburndown.controller;

import com.codecool.jopburndown.database.DbHandler;
import com.codecool.jopburndown.model.Board;
import org.hibernate.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import spark.ModelAndView;
import spark.Request;
import spark.Response;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * MainController class is responsible for rendering the main page, which is a form, where
 * one can choose the difficulty level.
 */
public class MainController {

    private static final Logger logger = LoggerFactory.getLogger(MainController.class);

    public static ModelAndView renderIndex(Request req, Response res, Session session) {
        if (!req.session().attributes().contains("user")) {
            return UserController.renderLogin(req, res);
        }
        HashMap<String, List<Board>> map = new HashMap<>();
        map.put("boards",Board.getAllScore(session));
        logger.info("Index page access");
        return new ModelAndView(map,"form");
    }

    /**
     * This method renders the main page. It will return a Spark ModelAndView object.
     * @param req
     * @param res
     * @return
     */
    public static ModelAndView renderDifficultyForm(Request req, Response res, Session session) {
        HashMap<String, List<Board>> map = new HashMap();
        map.put("boards",Board.getAllScore(session));
        return new ModelAndView(map,"form");
    }

    public static ModelAndView renderIndexWithUser(Request req, Response res) {

        HashMap<String, String> map = new HashMap<>();
        map.put("username", req.session().attribute("username"));
        return new ModelAndView(map, "index");
    }

    /**
     * Upon a won game, saves the completed time
     * with a min-sec-ms format and starts the
     * process to save it to the database.
     * @param req  Request
     * @param response Response
     * @param session Session
     * @return Response
     */
    public static Response getWinningTime( Request req ,Response response, Session session){
        if(req.session().attributes().contains("time")){
            Date oldTime = req.session().attribute("time");
            long milSec = new Date().getTime() - oldTime.getTime();
            Date date = new Date(milSec);
            SimpleDateFormat dateFormat = new SimpleDateFormat("mm-ss-SS");
            String format =  dateFormat.format(date);
            req.session().removeAttribute("time");
            DbHandler dbHandler =   DbHandler.getDbHandlerInstance();
            dbHandler.saveScoretoBoard(req,session,format);
        }
        return response;
    }
}
