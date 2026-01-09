package org.example;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class playerMovement extends Player{
    private int dice = 6;
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
        setAvailableMovement(rollDice());
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

    Location stockholm = new Location("Stockholm", 4, 5);
    Location Copenhagen =  new Location("Copenhagen", 4, 3);
    Location Oslo = new Location("Oslo", 1, 0);
    Location Berlin = new Location("Berlin", 1, 1);
    Location[] locations = {
        stockholm, Copenhagen, Oslo, Berlin
    };

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

    //Dice roll method
    public int rollDice() {
        int roll = (int) ((dice * Math.random()+1));
        System.out.println(" ");
        System.out.println("Rolled a " + roll);
        return roll;
    }
}

record Transport(String transportationMethod, int dices, int cost){}

record Location(String city, int x, int y){}


