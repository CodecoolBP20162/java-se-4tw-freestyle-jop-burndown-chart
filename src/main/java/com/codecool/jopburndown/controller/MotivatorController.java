package com.codecool.jopburndown.controller;

import com.codecool.jopburndown.model.Motivator;
import org.json.simple.JSONObject;
import spark.ModelAndView;
import spark.Request;
import spark.Response;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by petya on 2017.06.01..
 */
public class MotivatorController {

    private static Motivator motivator = new Motivator();

    public static JSONObject getMotivationalMessage(Request req, Response res){
        JSONObject jsonObj = new JSONObject();
        jsonObj.put("message", motivator.getMotivationMessage());
        res.type("application/json");
        return jsonObj;
    }

    public static Response setNewMotivationalMessage(Request req, Response res){
        String message = req.queryParams("message");
        motivator.setMotivationMessages(message);
        res.redirect("/");
        return res;
    }
}
