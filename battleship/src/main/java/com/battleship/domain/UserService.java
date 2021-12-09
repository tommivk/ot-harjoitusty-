package com.battleship.domain;

import java.util.ArrayList;
import java.util.List;

import com.battleship.dao.UserDao;

public class UserService {

    private UserDao userDao;

    public UserService(UserDao userDao) {
        this.userDao = userDao;
    }

    public boolean createUser(String userName) {
        try {
            userDao.create(userName);
        } catch (Exception e) {
            return false;
        }
        return true;
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
