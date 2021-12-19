package com.battleship.domain;

import java.util.ArrayList;
import java.util.List;

import com.battleship.dao.UserDao;

/**
 * Class that is used to interact with the users and the database
 */
public class UserService {

    private UserDao userDao;
    private User loggedPlayerOne;
    private User loggedPlayerTwo;
    private String databaseAdress;

    public UserService(UserDao userDao, String databaseAdress) {
        this.userDao = userDao;
        this.databaseAdress = databaseAdress;
    }

    public User getLoggedPlayerOne() {
        return this.loggedPlayerOne;
    }

    public User getLoggedPlayerTwo() {
        return this.loggedPlayerTwo;
    }

    public void logout() {
        this.loggedPlayerOne = null;
        this.loggedPlayerTwo = null;
    }

    /**
     * Stores new user in database
     * 
     * @param username users name that will be stored
     * 
     * @return returns true if succesfull
     */
    public boolean createUser(String username) {
        try {
            User user = userDao.create(this.databaseAdress, username);
            if (user == null) {
                return false;
            }
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * logs in player one
     * 
     * @param username users name will be logged in
     * 
     * @return returns true if succesfull
     */
    public boolean playerOneLogin(String username) {
        try {
            User user = userDao.login(this.databaseAdress, username);
            if (user == null) {
                return false;
            }
            this.loggedPlayerOne = user;
            return true;

        } catch (Exception e) {
            return false;
        }
    }

    /**
     * logs in player two
     * 
     * @param username users name will be logged in
     * 
     * @return returns true if succesfull
     */
    public boolean playerTwoLogin(String username) {
        try {
            User user = userDao.login(this.databaseAdress, username);
            if (user == null) {
                return false;
            }
            this.loggedPlayerTwo = user;
            return true;

        } catch (Exception e) {
            return false;
        }
    }

    public User getUser(String username) {
        try {
            return userDao.getUser(this.databaseAdress, username);
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * Gets all users that are stored in database
     * 
     * @return List of User objects
     */
    public List<User> getAllUsers() {
        List<User> users = new ArrayList<User>();
        try {
            users = userDao.getAll(this.databaseAdress);
            return users;
        } catch (Exception e) {

        }
        return users;
    }
}
