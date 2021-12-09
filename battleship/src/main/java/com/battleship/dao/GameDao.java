package com.battleship.dao;

import com.battleship.domain.Game;
import com.battleship.domain.User;

public interface GameDao {
    Game createGame(User playerOne, User playerTwo) throws Exception;
}
