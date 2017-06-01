package com.codecool.jopburndown;


import com.codecool.jopburndown.controller.BoardController;
import com.codecool.jopburndown.controller.MainController;
import com.codecool.jopburndown.model.Board;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import spark.template.thymeleaf.ThymeleafTemplateEngine;
import static spark.Spark.*;

public class Main {

    private static final Logger logger = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args) {

        logger.info(" JOP-BurndownChart's Minesweeper server awake!");
        exception(Exception.class, (e, req, res) -> e.printStackTrace());
        staticFileLocation("/public");
        port(8888);

        get("/", MainController::renderDifficultyForm, new ThymeleafTemplateEngine());

        post("/get_size", BoardController::createNewBoard);

        get("/board", BoardController::showBoard, new ThymeleafTemplateEngine());

        post("/retrieve_data", BoardController::infoAboutSquare);

        get("/evaluate", BoardController::countMines);
    }
}
