package com.battleship.domain;

import java.util.ArrayList;
import java.util.List;

import com.battleship.dao.UserDao;

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

    /**
     * Stores new user in database
     * 
     * @param userName users name that will be stored
     * 
     * @return returns true if succesfull
     */
    public boolean createUser(String userName) {
        try {
            User user = userDao.create(this.databaseAdress, userName);
            if (user == null) {
                return false;
            }
            return true;
        } catch (Exception e) {
            System.out.println(e);
            return false;
        }
    }

    /**
     * logs in player one
     * 
     * @param userName users name will be logged in
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
     * @param userName users name will be logged in
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

    /**
     * Gets all users that are stored in database
     */
    public List<User> getAllUsers() {
        List<User> users = new ArrayList<User>();
        try {
            users = userDao.getAll(this.databaseAdress);

            for (int i = 0; i < users.size(); i++) {
                System.out.println(users.get(i).getName());
            }

            return users;
        } catch (Exception e) {

        }
        return users;
    }
}
