package com.battleship.dao;

import com.battleship.domain.User;
import java.util.List;

public interface UserDao {
    User create(String databaseAddress, String username) throws Exception;

    User login(String databaseAdress, String username) throws Exception;

    List<User> getAll(String databaseAdress) throws Exception;
}
