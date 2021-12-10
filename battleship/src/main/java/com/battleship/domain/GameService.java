package com.battleship.domain;

import com.battleship.dao.GameDao;

public class GameService {
    private GameDao gameDao;
    private Game currentGame;
    private String databaseAdress;

    public GameService(GameDao gameDao, String databaseAdress) {
        this.gameDao = gameDao;
        this.databaseAdress = databaseAdress;
    }

    public boolean createGame(User playerOne, User playerTwo) {
        try {
            Game game = gameDao.createGame(this.databaseAdress, playerOne, playerTwo);
            if (game == null) {
                return false;
            }
            this.currentGame = game;
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public int getPlayerShotCount(int playerId) {
        try {
            int count = gameDao.getPlayerShotCount(this.databaseAdress, playerId);
            System.out.println("Count: " + count);
            return count;
        } catch (Exception e) {
            return 0;
        }
    }

    public void addPlayerOneShot() {
        try {
            gameDao.addPlayerOneShot(this.databaseAdress, currentGame.getGameId());

        } catch (Exception e) {

        }
    }

    public void addPlayerTwoShot() {
        try {
            gameDao.addPlayerTwoShot(this.databaseAdress, currentGame.getGameId());

        } catch (Exception e) {

        }
    }

    public Game getGame() {
        return this.currentGame;
    }
}
