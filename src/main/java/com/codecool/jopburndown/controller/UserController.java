package com.codecool.jopburndown.controller;

import com.codecool.jopburndown.database.DbHandler;
import org.hibernate.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import spark.ModelAndView;
import spark.Request;
import spark.Response;

import java.util.HashMap;
import java.util.Map;

/**
 * Handles the user login and register
 */
public class UserController {

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);
    private static Map<String, String> map = new HashMap();

    /**
     * Renders the login page
     *
     * @param req Request
     * @param res Response
     * @return ModelAndView
     */
    public static ModelAndView renderLogin(Request req, Response res) {
        logger.info("Login page accessed from ip: " + req.ip());
        return new ModelAndView(map, "login");
    }

    /**
     * Logouts the user, removes user from current session
     *
     * @param req Request
     * @return ModelAndView
     */
    public static ModelAndView logout(Request req) {
        req.session().removeAttribute("user");
        return new ModelAndView(map, "login");
    }

    /**
     * Renders the register page
     *
     * @param req Request
     * @param res Response
     * @return ModelAndView
     */
    public static ModelAndView renderRegister(Request req, Response res) {
        logger.info("Register page accessed from ip: " + req.ip());
        return new ModelAndView(map, "register");
    }

    /**
     * Redirects to the login page
     *
     * @param req Request
     * @param session Session
     * @return ModelAndView
     */
    public static ModelAndView submitRegister(Request req, Session session) {
        DbHandler dbHandler = DbHandler.getDbHandlerInstance();
        dbHandler.saveUserToDB(req, session);
        logger.info("Successfully submitted");
        return new ModelAndView(map, "login");
    }

    /**
     * Redirects to the board page
     *
     * @param req Request
     * @param session Session
     * @return ModelAndView
     */
    public static ModelAndView submitUser(Request req, Session session) {
        DbHandler dbHandler = DbHandler.getDbHandlerInstance();
        dbHandler.getUserFromDB(req, session);
        logger.info("Successful login");
        return new ModelAndView(map, "form");
    }
}


