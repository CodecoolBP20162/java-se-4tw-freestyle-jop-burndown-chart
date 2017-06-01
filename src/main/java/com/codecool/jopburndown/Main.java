package com.codecool.jopburndown;


import com.codecool.jopburndown.controller.BoardController;
import com.codecool.jopburndown.controller.MainController;
import spark.template.thymeleaf.ThymeleafTemplateEngine;
import static spark.Spark.*;

public class Main {
    public static void main(String[] args) {

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
