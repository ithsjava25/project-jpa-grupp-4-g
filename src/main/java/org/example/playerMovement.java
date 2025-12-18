package org.example;

//@Todo player movement
//with continents, countries to cites

public class playerMovement extends Player{

    //commented out parts are for running tests of the program. Has modes for 2 - 4 players
//    boolean wonGame = false;
//    playerMovement player1 = new playerMovement();
//    playerMovement player2 = new playerMovement();
//    playerMovement player3 = new playerMovement();
//    playerMovement player4 = new playerMovement();
//
//        System.out.println("How many players?? 2 - 4 players:");
//    String amountIn = IO.readln("Input amount: ");
//    int playerAmount = 0;
//        try {
//        playerAmount = Integer.parseInt(amountIn);
//    } catch (NumberFormatException e) {
//        System.out.println("Not a number");
//    }
//
//        if(playerAmount < 2){
//        System.out.println("Less than 2");
//    } else if(playerAmount == 2) {
//        String P1 = IO.readln("Please enter the first player name: ");
//        String P2 = IO.readln("Please enter the second player name: ");
//        player1.setPlayerName(P1);
//        player2.setPlayerName(P2);
//        IO.readln("Press enter to start game");
//        while (!wonGame) {
//            player1.play();
//            player2.play();
//            if (player1.checkScore()){
//                System.out.println(player1.getPlayerName() + " Wins the game");
//                wonGame = true;
//            } else if (player2.checkScore()) {
//                System.out.println(player2.getPlayerName() + " Wins the game");
//                wonGame = true;
//            }else  {
//                wonGame = false;
//            }
//        }
//    }
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


    private country country;

    public void setCountry(String country, String city, double Lat, double Longitude) {
        this.country = new country(country, city, Lat, Longitude);
    }

    private String[] continents = {"Europe",
        "Asia",
        "North America",
        "South America",
        "Africa",
        "Australia",
        "Antarctica"};

    private String[] board = {
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

    //sets the playerPosition that matches a board.
    private String[] playerPos = new String[board.length];


    public void setContinents(String[] continents) {
        this.continents = continents;
    }

    public void setBoard(String[] board) {
        this.board = board;
    }


    //starts the play for a player

    void play(){
       System.out.println("________________________________");
           IO.readln(playerName + " your turn!! Roll dice by pressing enter");
           if (playerPos[playerPos.length-1] == null){
               playerPos = playRound(playerPos, board);
               checkIfPlayerHasPenalties();
           } else {
               System.out.println(playerName + " at end of board");
               increaseScore();
               System.out.println(playerName + " score: " + getPlayerScore());
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
            }
        }
        return playerPos;
    }

    //method for playermovement
    /*TODO update method to move with kilometers or longitude/latitude positioning
    *  ADD functionality depending on transportation method and the movement the transport has*/

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

    //Dice roll method
    public int rollDice(int dice) {
        int roll = (int) ((dice * Math.random()+1));
        System.out.println(" ");
        System.out.println("Rolled " + roll);
        return roll;
    }
}

record country (String countryName, String City, double latitude, double longitude){}
