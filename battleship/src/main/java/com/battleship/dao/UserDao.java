package com.battleship.dao;

import com.battleship.domain.User;
import java.util.List;

public interface UserDao {
    User create(String username) throws Exception;

    List<User> getAll();
}
