package com.codecool.jopburndown.model;

import javax.persistence.*;
import org.mindrot.jbcrypt.BCrypt;

@Entity
@Table(name = "board_user")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "user_id")
    private int id;
    private String username;
    private String password;

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    /**
     * Authenticates the user password with the given input
     *
     * @param password String
     * @return boolean
     */
    public boolean authenticate(String password) {
        return BCrypt.checkpw(password, this.password);
    }

    /**
     * Returns the current user's name
     *
     * @return String
     */
    public String getUsername() {
        return username;
    }
}
