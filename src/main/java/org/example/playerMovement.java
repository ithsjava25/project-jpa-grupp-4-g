package org.example;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

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
//        }
//        }
// }
    private int selectedTransport;
    private int availableMovement;
    private int playerPosX;
    private int playerPosY;
    record destinationPos(int destinationX, int destinationY) {}
    private destinationPos destination;

    private Transport bicycle = new Transport("bicycle", 2, 5);

    private Transport car = new Transport("car", 4, 50);

    private Transport plane = new Transport("plane", 6, 500);

    private Transport[] transMethods = new Transport[]{bicycle, car, plane};

    public void setPlayerPosX(int playerPosX) {
        if (playerPosX >= 0) {
            this.playerPosX = playerPosX;
        }  else {
            System.out.println("Invalid, playerPosX must be greater than or equal to zero!");
        }
    }

    public int getPlayerPosX() {
        return playerPosX;
    }

    public void setPlayerPosY(int playerPosY) {
        if (playerPosY >= 0){
            this.playerPosY = playerPosY;
        } else {
            System.out.println("Invalid, playerPosY must be greater than or equal to zero!");
        }
    }

    public int getPlayerPosY() {
        return playerPosY;
    }

    public void playerMoveUp(){
        this.playerPosY++;
    }

    public void playerMoveDown(){
        if (playerPosY > 0){
            this.playerPosY--;
        } else {
            System.out.println("Invalid, player cant move less than zero");
        }
    }

    public void playerMoveLeft(){
        if(playerPosX > 0 ) {
            this.playerPosX--;
        } else {
            System.out.println("Invalid, player cant move less than zero!");
        }
    }

    public void playerMoveRight(){
        this.playerPosX++;
    }

    public void setDestinationPos(int destinationX, int destinationY) {
        this.destination = new destinationPos(destinationX, destinationY);
    }

    public int getDestinationPosX() {
        if(this.destination == null){
            throw new NullPointerException("Destination has not been set");
        }
        return this.destination.destinationX();
    }

    public int getDestinationPosY() {
        if(this.destination == null){
            throw new NullPointerException("Destination has not been set");
        }
        return this.destination.destinationY();
    }

    public boolean checkIfPlayerIsAtDestination(){
        if (this.playerPosX == getDestinationPosX() && this.playerPosY == getDestinationPosY()) {
            setAvailableMovement(0);
            return true;
        }
        return false;
    }

    public void setAvailableMovement(int availableMovement) {
        this.availableMovement = availableMovement;
    }

    public int getAvailableMovement() {
        return availableMovement;
    }

    public boolean checkMovementIsZero(){
        if (this.availableMovement == 0) {
            return true;
        } else if (this.availableMovement < 0) {
            System.out.println("Invalid movement cant be less than zero");
        }
        return false;
    }

    public void playerTurn(){
        setAvailableMovement(rollDice(6));
        while (!checkMovementIsZero()){
            String input = IO.readln("choose movement");
            input = input.toLowerCase();
            switch (input) {
                case "up" -> {
                    playerMoveUp();
                    availableMovement--;
                }
                case "down" -> {
                    playerMoveDown();
                    availableMovement--;
                }
                case "left" -> {
                    playerMoveLeft();
                    availableMovement--;
                }
                case "right" -> {
                    playerMoveRight();
                    availableMovement--;
                }default ->  {
                    System.out.println("Invalid input!");
                }
            }
            if (checkIfPlayerIsAtDestination()){
                increaseScore();
            }
            System.out.println(" ");
            System.out.println("Player at position X: " + playerPosX + " Y: " + playerPosY );
        }
    }

    private String[] continents = {
        "Europe",
        "Asia",
        "North America",
        "South America",
        "Africa",
        "Australia",
        "Antarctica"};

    Location stockholm = new Location("Stockholm", 4, 5);
    Location Copenhagen =  new Location("Copenhagen", 4, 3);
    Location Oslo = new Location("Oslo", 1, 0);
    Location Berlin = new Location("Berlin", 1, 1);
    Location[] locations = {
        stockholm, Copenhagen, Oslo, Berlin
    };
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

    public void chooseTransportation() throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        for (int i = 0; i < transMethods.length; i++) {
            System.out.println("Nr: "+ (i+1) + " - " + transMethods[i].transportationMethod() + " Cost: " +  transMethods[i].cost() + " Dice: " + transMethods[i].dices());
        }
        while (true){
            System.out.println("Choose transportation: ");
            String input = br.readLine();
            if (input.isEmpty()) {
                System.out.println("Invalid input, needs to be a number");
                continue;
            }
            int choice = 0;
            try{
                choice = Integer.parseInt(input);
            } catch (NumberFormatException e) {
                System.out.println("Invalid input, needs to be a number");
                continue;
            }
            switch (choice) {
                case 1 -> {
                    if (checkIfPlayerHasEnoughCredits(transMethods[0].cost())){
                        this.selectedTransport = 0;
                        removeCredits(transMethods[0].cost());
                    };
                    return;
                }
                case 2 -> {

                    if (checkIfPlayerHasEnoughCredits(transMethods[1].cost())){
                        this.selectedTransport = 1;
                        removeCredits(transMethods[1].cost());
                    };
                    return;
                }
                case 3 -> {

                    if (checkIfPlayerHasEnoughCredits(transMethods[2].cost())){
                        this.selectedTransport = 2;
                        removeCredits(transMethods[2].cost());
                    };
                    return;
                }default -> {
                    System.out.println("Invalid input, needs to be a number");
                }
            }
        }
    }

    //sets the playerPosition that matches a board.
//    private String[] playerPos = new String[board.length];
//
//
//    public void setContinents(String[] continents) {
//        this.continents = continents;
//    }
//
//    public void setBoard(String[] board) {
//        this.board = board;
//    }

    //starts the play for a player
//    void play(){
//       System.out.println("________________________________");
//        System.out.println(playerName + ", your turn!");
//        System.out.println("Choose a transport method: ");
//        try {
//            chooseTransportation();
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
//        if (playerPos[playerPos.length-1] == null){
//           playerPos = playRound(playerPos, board);
//           checkIfPlayerHasPenalties();
//       } else {
//           System.out.println(playerName + " at end of board");
//           increaseScore();
//           System.out.println(playerName + " score: " + getPlayerScore());
//           playerPos = new String[board.length];
//       }
//    }
//
//    private String[] playRound(String[] playerPos, String[] board) {
//        playerPos = movePlayer(playerPos);
//        for (int i = 1; i < playerPos.length; i++){
//            if (playerPos[i] != null){
//                System.out.println(" ");
//                System.out.println(playerName + " on position " + i);
//                System.out.println(board[i]);
//                System.out.println("credits: " + getCredits());
//            }
//        }
//        return playerPos;
//    }
//
//    public String[] movePlayer(String[] playerPos){
//        String[] temp = new String[playerPos.length];
//        int newPos = 0;
//        for (int i = 0; i < playerPos.length; i++){
//            if (playerPos[i] != null){
//                newPos = i;
//            }
//        }
//        int newPosition = newPos + rollDice(transMethods[selectedTransport].dices());
//        if (newPosition < 0 || newPosition < playerPos.length){
//            temp[newPosition] = playerName;
//        } else {
//            temp[playerPos.length - 1] = playerName;
//        }
//        return temp;}

    //Dice roll method
    public int rollDice(int dice) {
        int roll = (int) ((dice * Math.random()+1));
        System.out.println(" ");
        System.out.println("Rolled a " + roll);
        return roll;
    }
}

record Transport(String transportationMethod, int dices, int cost){}

record Location(String city, int x, int y){}


