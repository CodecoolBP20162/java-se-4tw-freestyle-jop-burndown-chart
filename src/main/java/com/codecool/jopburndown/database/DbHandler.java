package com.codecool.jopburndown.database;

import com.codecool.jopburndown.model.Board;
import com.codecool.jopburndown.model.User;
import spark.Request;
import org.mindrot.jbcrypt.BCrypt;
import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class DbHandler {

    private static final Logger logger = LoggerFactory.getLogger(DbHandler.class);
    private static DbHandler DbHandlerInstance;
    private SessionFactory sessionFactory;
    private ArrayList<String> dbProps = DbConnProps.connectProps();
    private String DB_USER = dbProps.get(1);
    private String DB_PASSWORD = dbProps.get(2);

    /**
     * It calls the buildTablesWithUniqueConnection method
     */
    private DbHandler() {
        buildTablesWithUniqueConnection();
    }

    /**
     * It creates DbHandler Singleton Class
     *
     * @return DbHandler
     */
    public static DbHandler getDbHandlerInstance() {
        if (DbHandlerInstance == null) {
            DbHandlerInstance = new DbHandler();
        }
        return DbHandlerInstance;
    }

    /**
     * It returns sessionFactory attribute
     * @return SessionFactory
     */
    public SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    /**
     * It configures hibernate.cfg.xml with unique DB_user and DB_password and builds tables in DB
     */
    public void buildTablesWithUniqueConnection() {
        Configuration config = new Configuration();
        config.configure("hibernate.cfg.xml");
        config.getProperties().setProperty("hibernate.connection.password", DB_PASSWORD);
        config.getProperties().setProperty("hibernate.connection.username", DB_USER);
        sessionFactory = config.buildSessionFactory();
        logger.info("Test connection with the database created successfully.");
    }

    /**
     * It saves username and password to DB
     *
     * @param req Request
     * @param session Session
     */
    public void saveUserToDB(Request req, Session session) {
        session.beginTransaction();
        User user = new User(req.queryParams("username"), BCrypt.hashpw(req.queryParams("password"), BCrypt.gensalt(10)));
        session.save(user);
        session.getTransaction().commit();
        logger.info("Successfully saved the username and the password.");
    }

    /**
     * It gets username and password from DB
     *
     * @param req Request
     * @param session Session
     */
    public void getUserFromDB(Request req, Session session) {
        session.beginTransaction();
        List<User> users = session.createQuery("FROM User").list();
        String username = req.queryParams("username");
        String password = req.queryParams("password");
        for (User user : users) {
            if (user.authenticate(password)) {
                req.session().attribute("user", user);
                logger.info("Successful login");
            }
        }
        session.getTransaction().commit();
    }

    /**
     * Saves the score to the table upon successfully won game
     *
     * @param req     Request
     * @param session Session
     * @param score   String
     */
    public void saveScoreToBoard(Request req, Session session, String score) {
        session.beginTransaction();
        Board board = new Board(req.session().attribute("size"), score, req.session().attribute("user"));
        session.save(board);
        session.getTransaction().commit();
        logger.info("Successfully saved the current score.");
    }
}
