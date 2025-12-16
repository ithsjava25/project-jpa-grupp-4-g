package org.example;

public class App {
    public static void main(String[] args) {
        player player1 = new playerMovement();
        playerMovement player2 = new playerMovement();

        player1.setPlayerName("Player 1");
        player2.setPlayerName("Player 2");

        player2.play();
    }
}
