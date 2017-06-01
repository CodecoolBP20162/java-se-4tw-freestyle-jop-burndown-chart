package com.codecool.jopburndown;

import com.codecool.jopburndown.controller.MainController;
import com.codecool.jopburndown.controller.UserController;
import com.codecool.jopburndown.controller.BoardController;
import com.codecool.jopburndown.controller.MotivatorController;
import com.codecool.jopburndown.database.DbHandler;

import java.text.SimpleDateFormat;
import java.util.Date;

import static spark.Spark.*;

import spark.template.thymeleaf.ThymeleafTemplateEngine;
import spark.Request;
import spark.Response;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Main {

    static {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        System.setProperty("date", dateFormat.format(new Date()));
    }

    private static final Logger logger = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args) {

        DbHandler dbHandler = DbHandler.getDbHandlerInstance();
        SessionFactory sessionFactory = dbHandler.getSessionFactory();
        Session session = sessionFactory.openSession();

        logger.info(" JOP-BurndownChart's Minesweeper server awake!");
        exception(Exception.class, (e, req, res) -> e.printStackTrace());
        staticFileLocation("/public");
        port(8888);

        get("/login", UserController::renderLogin, new ThymeleafTemplateEngine());

        get("/register", UserController::renderRegister, new ThymeleafTemplateEngine());

        get("/", (Request req, Response res) -> {
            return new ThymeleafTemplateEngine().render(MainController.renderIndex(req, res, session));
        });

        post("/get_size", BoardController::createNewBoard);

        post("/winning_time", (Request req, Response res) -> {
            return MainController.getWinningTime(req, res, session);
        });

        get("/board", BoardController::showBoard, new ThymeleafTemplateEngine());

        post("/retrieve_data", BoardController::infoAboutSquare);

        post("/register", (Request req, Response res) -> {
            return new ThymeleafTemplateEngine().render(UserController.submitRegister(req, session));
        });

        post("/login", (Request req, Response res) -> {
            return new ThymeleafTemplateEngine().render(UserController.submitUser(req, session));
        });

        get("/logout", (Request req, Response res) -> {
            return new ThymeleafTemplateEngine().render(UserController.logout(req));
        });

        get("/", (Request req, Response res) -> {
            return new ThymeleafTemplateEngine().render(MainController.renderDifficultyForm(session));
        });

        post("/get_size", BoardController::createNewBoard);

        post("/retrieve_data", BoardController::infoAboutSquare);

        get("/evaluate", BoardController::countMines);

        get("/motivation", MotivatorController::getMotivationalMessage);

        post("/set_motivation", MotivatorController::setNewMotivationalMessage);
    }
}
