package com.codecool.jopburndown.database;
import com.codecool.jopburndown.model.Board;
import com.codecool.jopburndown.model.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import spark.Request;
import spark.Response;

import java.util.ArrayList;

public class DbHandler {

    private static final Logger logger = LoggerFactory.getLogger(DbHandler.class);
    private static DbHandler DbHandlerInstance;
    private SessionFactory sessionFactory;
    private ArrayList<String> dbProps = DbConnProps.connectProps();
    private String DB_USER = dbProps.get(1);
    private String DB_PASSWORD = dbProps.get(2);

    private DbHandler(){
        buildTablesWithUniqueConnection();
    };

    public static DbHandler getDbHandlerInstance() {
        if(DbHandlerInstance == null) {
            DbHandlerInstance = new DbHandler();
        }
        return DbHandlerInstance;
    }

    public SessionFactory getSessionFactory() {
        return sessionFactory;
    }



    public void buildTablesWithUniqueConnection(){

        Configuration config = new Configuration();
        config.configure("hibernate.cfg.xml");
        config.getProperties().setProperty("hibernate.connection.password",DB_PASSWORD);
        config.getProperties().setProperty("hibernate.connection.username",DB_USER);
        sessionFactory = config.buildSessionFactory();
        logger.info("Test connection with the database created successfully.");
    }

    public void saveUserToDB(Request req, Session session) {

        session.beginTransaction();
        User user = new User(req.queryParams("username"), req.queryParams("password"));
        session.save(user);
        session.getTransaction().commit();
        logger.info("Successfully saved the username and the password.");
    }

}
