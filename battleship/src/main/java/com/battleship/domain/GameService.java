package com.battleship.domain;

import com.battleship.dao.GameDao;

public class GameService {
    private GameDao gameDao;
    private Game currentGame;

    public GameService(GameDao gameDao) {
        this.gameDao = gameDao;
    }

    public boolean createGame(User playerOne, User playerTwo) {
        try {
            Game game = gameDao.createGame(playerOne, playerTwo);
            if (game == null) {
                return false;
            }
            this.currentGame = game;
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public Game getGame() {
        return this.currentGame;
    }
}
