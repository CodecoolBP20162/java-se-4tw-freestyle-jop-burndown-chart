package com.codecool.jopburndown.database;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Properties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DbConnProps {

    private static final Logger logger = LoggerFactory.getLogger(DbConnProps.class);

    /**
     * It loads the data from properties.txt needed for the hibernate connection
     * @return ArrayList<String>
     */
    public static ArrayList<String> connectProps() {

        Properties prop = new Properties();
        InputStream input = null;
        ArrayList<String> propList = new ArrayList<>();
        String pathToDB = "src/main/resources/properties.txt";
        try {
            input = new FileInputStream(pathToDB);
            prop.load(input); // load properties.txt
            propList.add(prop.getProperty("database"));
            propList.add(prop.getProperty("username"));
            propList.add(prop.getProperty("password"));
            logger.info("Successfully connected to database {} with {} as username", prop.getProperty("database"), prop.getProperty("username"));
            return propList;

        } catch (IOException ex) {
            ex.printStackTrace();
            logger.error("Connection failed by reason of file error");
        } finally {
            if (input != null) {
                try {
                    input.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }
}