package com.codecool.jopburndown;


import com.codecool.jopburndown.controller.FieldController;
import com.codecool.jopburndown.controller.MainController;
import spark.template.thymeleaf.ThymeleafTemplateEngine;
import static spark.Spark.*;
import com.codecool.jopburndown.model.Board;

public class Main {
    public static void main(String[] args) {

        exception(Exception.class, (e, req, res) -> e.printStackTrace());
        staticFileLocation("/public");
        port(8888);

        get("/", MainController::renderIndex, new ThymeleafTemplateEngine());

        post("/get_size", FieldController::createNewBoard);

        get("/board", FieldController::showBoard, new ThymeleafTemplateEngine());

        post("/retrieve_data", FieldController::infoAboutSquare);

    }
}
