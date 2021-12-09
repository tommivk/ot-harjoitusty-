package com.battleship.domain;

import java.util.ArrayList;
import java.util.List;

import com.battleship.dao.UserDao;

public class UserService {

    private UserDao userDao;
    private User loggedPlayerOne;
    private User loggedPlayerTwo;

    public UserService(UserDao userDao) {
        this.userDao = userDao;
    }

    public User getLoggedPlayerOne() {
        return this.loggedPlayerOne;
    }

    public User getLoggedPlayerTwo() {
        return this.loggedPlayerTwo;
    }

    public boolean createUser(String userName) {
        try {
            User user = userDao.create(userName);
            if (user == null) {
                return false;
            }
            return true;
        } catch (Exception e) {
            System.out.println(e);
            return false;
        }
    }

    public boolean playerOneLogin(String username) {
        try {
            User user = userDao.login(username);
            if (user == null) {
                return false;
            }
            this.loggedPlayerOne = user;
            return true;

        } catch (Exception e) {
            return false;
        }
    }

    public boolean playerTwoLogin(String username) {
        try {
            User user = userDao.login(username);
            if (user == null) {
                return false;
            }
            this.loggedPlayerTwo = user;
            return true;

        } catch (Exception e) {
            return false;
        }
    }

    public List<User> getAllUsers() {
        List<User> users = new ArrayList<User>();
        try {
            users = userDao.getAll();

            for (int i = 0; i < users.size(); i++) {
                System.out.println(users.get(i).getName());
            }

            return users;
        } catch (Exception e) {

        }
        return users;
    }
}
