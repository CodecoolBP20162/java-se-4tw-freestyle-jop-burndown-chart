package com.codecool.jopburndown.controller;

import com.codecool.jopburndown.database.DbHandler;
import com.codecool.jopburndown.model.User;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.mindrot.jbcrypt.BCrypt;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import spark.ModelAndView;
import spark.Request;
import spark.Response;
import org.hibernate.Session;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Handles the user login and register
 */
public class UserController {

    private static Map<String, String> map = new HashMap();
    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    /**
     * Renders the login page
     * @param req Request
     * @param res Response
     * @return ModelAndView
     */
    public static ModelAndView renderLogin(Request req, Response res){
        logger.info("Login page accessed from ip: " + req.ip());
        return new ModelAndView(map,"login");
    }

    /**
     * Renders the register page
     * @param req Request
     * @param res Response
     * @return ModelAndView
     */
    public static ModelAndView renderRegister(Request req, Response res){
        logger.info("Register page accessed from ip: " + req.ip());
        return new ModelAndView(map,"register");
    }

    /**
     * Redirects to the login page
     * @param req
     * @param session
     * @return
     */
    public static ModelAndView submitRegister(Request req, Session session){
        DbHandler dbHandler = DbHandler.getDbHandlerInstance();
        dbHandler.saveUserToDB(req, session);
        logger.info("Successfully submitted");
        return new ModelAndView(map, "login");
    }

    /**
     * Redirects to the board page
     * @param req
     * @param session
     * @return
     */
    public static ModelAndView submitUser(Request req, Session session){
        DbHandler dbHandler = DbHandler.getDbHandlerInstance();
        dbHandler.getUserFromDB(req, session);
        logger.info("Successful login");
        return new ModelAndView(map, "board");
    }
}


