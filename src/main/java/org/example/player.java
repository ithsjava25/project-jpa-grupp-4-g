package org.example;

abstract class player {
    String playerName;
    int playerScore = 0;


    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    public String getPlayerName() {
        return playerName;
    }

    public void increaseScore() {
        playerScore++;
    }

    public int getPlayerScore() {
        return playerScore;
    }

    public boolean checkScore() {
        if (playerScore == 5) {
            return true;
        }
        return false;
    }
}
