package com.battleship.domain;

import java.util.ArrayList;
import java.util.List;

import com.battleship.dao.UserDao;

public class UserService {

    private UserDao userDao;
    private User loggedUser;

    public UserService(UserDao userDao) {
        this.userDao = userDao;
    }

    public User getLoggedUser() {
        return this.loggedUser;
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

    public boolean login(String username) {
        try {
            User user = userDao.login(username);
            if (user == null) {
                return false;
            }
            this.loggedUser = user;
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
