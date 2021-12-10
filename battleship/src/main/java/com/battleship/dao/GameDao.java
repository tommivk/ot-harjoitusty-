package com.battleship.dao;

import com.battleship.domain.Game;
import com.battleship.domain.User;

public interface GameDao {
    Game createGame(String databaseAdress, User playerOne, User playerTwo) throws Exception;

    int getPlayerShotCount(String databaseAdress, int playerId) throws Exception;

    void addPlayerOneShot(String databaseAdress, int gameId) throws Exception;

    void addPlayerTwoShot(String databaseAdress, int gameId) throws Exception;
}
