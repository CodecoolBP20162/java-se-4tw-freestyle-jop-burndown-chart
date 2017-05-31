package com.codecool.jopburndown.model;


import org.mindrot.jbcrypt.BCrypt;

public class User {

    public boolean authenticate(String password){
        return BCrypt.checkpw(password, "");
    }

}
