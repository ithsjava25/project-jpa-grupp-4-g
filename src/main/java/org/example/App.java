package org.example;

public class App {
    public static void main(String[] args) {
        boolean wonGame = false;
        playerMovement player1 = new playerMovement();
        playerMovement player2 = new playerMovement();
        playerMovement player3 = new playerMovement();
        playerMovement player4 = new playerMovement();

        System.out.println("How many players?? 2 - 4 players:");
        String amountIn = IO.readln("Input amount: ");
        int playerAmount = 0;
            try {
                playerAmount = Integer.parseInt(amountIn);
            } catch (NumberFormatException e) {
                System.out.println("Not a number");
            }

        if(playerAmount < 2){
        System.out.println("Less than 2");
    } else if(playerAmount == 2) {
        String P1 = IO.readln("Please enter the first player name: ");
        String P2 = IO.readln("Please enter the second player name: ");
        player1.setPlayerName(P1);
        player2.setPlayerName(P2);
        IO.readln("Press enter to start game");
        while (!wonGame) {
            player1.play();
            player2.play();
            if (player1.checkScore()){
                System.out.println(player1.getPlayerName() + " Wins the game");
                wonGame = true;
            } else if (player2.checkScore()) {
                System.out.println(player2.getPlayerName() + " Wins the game");
                wonGame = true;
            }else  {
                wonGame = false;
            }
        }
    }
    }
}


