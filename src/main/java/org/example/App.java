package org.example;

public class App {
    public static void main(String[] args) {


        boolean wonGame = false;
        playerMovement player1 = new playerMovement();
        playerMovement player2 = new playerMovement();
        playerMovement player3 = new playerMovement();
        playerMovement player4 = new playerMovement();


//        String amountIn = IO.readln("How many players?? 2 - 4 players");
//        int playerAmount = 0;
//        try {
//            playerAmount = Integer.parseInt(amountIn);
//        } catch (NumberFormatException e) {
//            System.out.println("Not a number");
//        }
//
//        if(playerAmount < 2){
//            System.out.println("Less than 2");
//        } else if(playerAmount == 2) {
//            String P1 = IO.readln("Please enter the first player name: ");
//            String P2 = IO.readln("Please enter the second player name: ");
//            player1.setPlayerName(P1);
//            player2.setPlayerName(P2);
//
//            IO.readln("Press enter to start game");
//            while (!wonGame) {
//                player1.play();
//                player2.play();
//                if (player1.checkScore()){
//                    System.out.println(player1.getPlayerName() + " Wins the game");
//                    wonGame = true;
//                } else if (player2.checkScore()) {
//                    System.out.println(player2.getPlayerName() + " Wins the game");
//                    wonGame = true;
//                }else  {
//                    wonGame = false;
//                }
//            }
//        } else if (playerAmount == 3) {
//            String P1 = IO.readln("Please enter the first player name: ");
//            String P2 = IO.readln("Please enter the second player name: ");
//            String P3 = IO.readln("Please enter the third player name: ");
//            player1.setPlayerName(P1);
//            player2.setPlayerName(P2);
//            player3.setPlayerName(P3);
//
//
//            IO.readln("Press enter to start game");
//            while (!wonGame) {
//                player1.play();
//                player2.play();
//                player3.play();
//                if (player1.checkScore()){
//                    System.out.println(player1.getPlayerName() + " Wins the game");
//                    wonGame = true;
//                } else if (player2.checkScore()) {
//                    System.out.println(player2.getPlayerName() + " Wins the game");
//                    wonGame = true;
//                } else if (player3.checkScore()) {
//                    System.out.println(player3.getPlayerName() + " Wins the game");
//                    wonGame = true;
//                } else  {
//                    wonGame = false;
//                }
//            }
//        }else if (playerAmount == 4) {
//            String P1 = IO.readln("Please enter the first player name: ");
//            String P2 = IO.readln("Please enter the second player name: ");
//            String P3 = IO.readln("Please enter the third player name: ");
//            String P4 = IO.readln("Please enter the fourth player name: ");
//            player1.setPlayerName(P1);
//            player2.setPlayerName(P2);
//            player3.setPlayerName(P3);
//            player4.setPlayerName(P4);
//            IO.readln("Press enter to start game");
//
//            while (!wonGame) {
//                player1.play();
//                player2.play();
//                player3.play();
//                player4.play();
//                if (player1.checkScore()){
//                    System.out.println(player1.getPlayerName() + " Wins the game");
//                    wonGame = true;
//                }
//                else if (player2.checkScore()) {
//                    System.out.println(player2.getPlayerName() + " Wins the game");
//                    wonGame = true;
//                } else if (player3.checkScore()) {
//                    System.out.println(player3.getPlayerName() + " Wins the game");
//                    wonGame = true;
//                }else  if (player4.checkScore()) {
//                    System.out.println(player4.getPlayerName() + " Wins the game");
//                    wonGame = true;
//                } else {
//                    wonGame = false;
//                }
//            }
//        }


    }


}
