package com.codecool.jopburndown.controller;

import com.codecool.jopburndown.model.Motivator;
import org.json.simple.JSONObject;
import spark.ModelAndView;
import spark.Request;
import spark.Response;

import java.util.HashMap;
import java.util.Map;

/**
 * This class handles the Motivator objects.
 */
public class MotivatorController {

    private static Motivator motivator = Motivator.getInstance();

    /**
     * This method put a string from the motivator's deque into a JSONObject then give it back.
     * @param req for Spark
     * @param res for Spark
     * @return JSONObject for ajax request
     */
    public static JSONObject getMotivationalMessage(Request req, Response res){
        JSONObject jsonObj = new JSONObject();
        jsonObj.put("message", motivator.getMotivationMessage());
        res.type("application/json");
        return jsonObj;
    }

    /**
     *This method add a new motivational string to the motivator's limited deque,
     *  then remove its last element.
     * @param req for Spark
     * @param res for Spark
     * @return Response object for Spark
     */
    public static Response setNewMotivationalMessage(Request req, Response res){
        String message = req.queryParams("message");
        motivator.setMotivationMessages(message);
        res.redirect("/");
        return res;
    }
}
