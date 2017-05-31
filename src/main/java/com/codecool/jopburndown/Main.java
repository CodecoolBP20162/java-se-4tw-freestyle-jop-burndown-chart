package com.codecool.jopburndown;


import com.codecool.jopburndown.controller.FieldController;
import com.codecool.jopburndown.controller.MainController;
import com.codecool.jopburndown.controller.UserController;
import spark.template.thymeleaf.ThymeleafTemplateEngine;
import static spark.Spark.*;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Main {

    static{
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        System.setProperty("date",dateFormat.format(new Date()));
    }

    public static void main(String[] args) {

        exception(Exception.class, (e, req, res) -> e.printStackTrace());
        staticFileLocation("/public");
        port(8888);

        get("/login", UserController::renderLogin, new ThymeleafTemplateEngine());

        get("/register", UserController::renderRegister, new ThymeleafTemplateEngine());

        get("/", MainController::renderIndex, new ThymeleafTemplateEngine());

        post("/get_size", FieldController::createNewBoard);

        get("/board", FieldController::showBoard, new ThymeleafTemplateEngine());

    }
}
