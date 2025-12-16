package org.example;

//@Todo player movement
//with continents, countries to cites

public class playerMovement extends player{

//    void main(){
//
//        boolean wonGame = false;
//        playerMovement player1 = new playerMovement();
//        playerMovement player2 = new playerMovement();
//        playerMovement player3 = new playerMovement();
//        playerMovement player4 = new playerMovement();
//
//
////        String amountIn = IO.readln("How many players?? 2 - 4 players");
////        int playerAmount = 0;
////        try {
////            playerAmount = Integer.parseInt(amountIn);
////        } catch (NumberFormatException e) {
////            System.out.println("Not a number");
////        }
////
////        if(playerAmount < 2){
////            System.out.println("Less than 2");
////        } else if(playerAmount == 2) {
////            String P1 = IO.readln("Please enter the first player name: ");
////            String P2 = IO.readln("Please enter the second player name: ");
////            player1.setPlayerName(P1);
////            player2.setPlayerName(P2);
////
////            IO.readln("Press enter to start game");
////            while (!wonGame) {
////                player1.play();
////                player2.play();
////                if (player1.checkScore()){
////                    System.out.println(player1.getPlayerName() + " Wins the game");
////                    wonGame = true;
////                } else if (player2.checkScore()) {
////                    System.out.println(player2.getPlayerName() + " Wins the game");
////                    wonGame = true;
////                }else  {
////                    wonGame = false;
////                }
////            }
////        } else if (playerAmount == 3) {
////            String P1 = IO.readln("Please enter the first player name: ");
////            String P2 = IO.readln("Please enter the second player name: ");
////            String P3 = IO.readln("Please enter the third player name: ");
////            player1.setPlayerName(P1);
////            player2.setPlayerName(P2);
////            player3.setPlayerName(P3);
////
////
////            IO.readln("Press enter to start game");
////            while (!wonGame) {
////                player1.play();
////                player2.play();
////                player3.play();
////                if (player1.checkScore()){
////                    System.out.println(player1.getPlayerName() + " Wins the game");
////                    wonGame = true;
////                } else if (player2.checkScore()) {
////                    System.out.println(player2.getPlayerName() + " Wins the game");
////                    wonGame = true;
////                } else if (player3.checkScore()) {
////                    System.out.println(player3.getPlayerName() + " Wins the game");
////                    wonGame = true;
////                } else  {
////                    wonGame = false;
////                }
////            }
////        }else if (playerAmount == 4) {
////            String P1 = IO.readln("Please enter the first player name: ");
////            String P2 = IO.readln("Please enter the second player name: ");
////            String P3 = IO.readln("Please enter the third player name: ");
////            String P4 = IO.readln("Please enter the fourth player name: ");
////            player1.setPlayerName(P1);
////            player2.setPlayerName(P2);
////            player3.setPlayerName(P3);
////            player4.setPlayerName(P4);
////            IO.readln("Press enter to start game");
////
////            while (!wonGame) {
////                player1.play();
////                player2.play();
////                player3.play();
////                player4.play();
////                if (player1.checkScore()){
////                    System.out.println(player1.getPlayerName() + " Wins the game");
////                    wonGame = true;
////                }
////                else if (player2.checkScore()) {
////                    System.out.println(player2.getPlayerName() + " Wins the game");
////                    wonGame = true;
////                } else if (player3.checkScore()) {
////                    System.out.println(player3.getPlayerName() + " Wins the game");
////                    wonGame = true;
////                }else  if (player4.checkScore()) {
////                    System.out.println(player4.getPlayerName() + " Wins the game");
////                    wonGame = true;
////                } else {
////                    wonGame = false;
////                }
////            }
////        }
/// }

    @Override
    public void setPlayerName(String playerName) {
        super.setPlayerName(playerName);
    }

    public String[] continents = {"Europe",
        "Asia",
        "North America",
        "South America",
        "Africa",
        "Australia",
        "Antarctica"};

    public String[] board = {
        "Start",
        "Stockholm",
        "Copenhagen",
        "Oslo",
        "Helsinki",
        "Berlin",
        "Paris",
        "Luxembourg",
        "Lichenstein",
        "Madrid"};

    String[] playerPos = new String[board.length];

    public void setContinents(String[] continents) {
        this.continents = continents;
    }

    public void setBoard(String[] board) {
        this.board = board;
    }

    public String getPlayerName() {
        return this.playerName;
    }

   void play(){
       boolean endBoard = true;
       System.out.println(" ");
       System.out.println("________________________________");
           IO.readln(playerName + " your turn!! Roll dice by pressing enter");
           if (playerPos[playerPos.length-1] == null){
               playerPos = playRound(playerPos, board);
               checkIfPlayerHasPenalties();
           } else {
               endBoard = false;
               System.out.println(playerName + " at end of board");
               increaseScore();
               playerPos = new String[board.length];
           }
       }



    private String[] playRound(String[] playerPos, String[] board) {
        playerPos = movePlayer(playerPos);
        for (int i = 1; i < playerPos.length; i++){
            if (playerPos[i] != null){
                System.out.println(" ");
                System.out.println(playerName + " on position " + i);
                System.out.println(board[i]);
                System.out.println(" ");
            }
        }
        return playerPos;
    }

    public String[] movePlayer(String[] playerPos){
       String[] temp = new String[playerPos.length];
       int newPos = 0;
       for (int i = 0; i < playerPos.length; i++){
           if (playerPos[i] != null){
               newPos = i;
           }
       }
       int newPosition = newPos + rollDice(4);
       if (newPosition < 0 || newPosition < playerPos.length){
           temp[newPosition] = playerName;
       } else {
           temp[playerPos.length - 1] = playerName;
       }
        return temp;
   }

    public int rollDice(int dice) {
        int roll = (int) ((dice * Math.random()+1));
        System.out.println(" ");
        System.out.println("Rolled " + roll);
        System.out.println(" ");
        return roll;
    }
}

