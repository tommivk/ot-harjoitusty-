package com.battleship.domain;

/**
 * User of the application
 */
public class User {
    private String name;
    private int id;

    public User(String name, int id) {
        this.name = name;
        this.id = id;
    }

    public User(String name) {
        this.name = name;
    }

    /**
     * Gets users username
     * 
     * @return username of the user
     */
    public String getName() {
        return this.name;
    }

    /**
     * Gets users id
     * 
     * @return id of the user
     */
    public int getId() {
        return this.id;
    }

}
