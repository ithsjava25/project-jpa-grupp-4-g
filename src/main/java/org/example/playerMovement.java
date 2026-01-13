package org.example;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

public class playerMovement extends Player{
    private int dice = 6;
    private int selectedTransportDice;
    private int availableMovement;
    private int playerPosX;
    private int playerPosY;
    record destinationPos(int destinationX, int destinationY) {}
    private destinationPos destination;
    private int playerTurnCount = 0;

    public int getTransDiceCount(){
        return selectedTransportDice;
    }

    public int getTurns(){
        return playerTurnCount;
    }
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
    public void autoMove(){
        for(int i = 0; i < availableMovement; i++){
            if (playerPosX != getDestinationPosX()){
                if(playerPosX > getDestinationPosX()){
                    playerMoveLeft();
                } else {
                    playerMoveRight();
                }
            } else if (playerPosY != getDestinationPosY()) {
                if (playerPosY > getDestinationPosY()){
                    playerMoveDown();
                } else{
                    playerMoveUp();
                }
            }
            availableMovement--;
        }
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
            System.out.println(playerName + " has arrived at destination. +1 score and 500 credits");
            addCredits(1000);
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

    public void playerTurn(List<Transport> transports) throws IOException {
        chooseTransportation(transports);
        setAvailableMovement(rollDice(selectedTransportDice));
        System.out.println(playerName + " turn");
        System.out.println("Score: " + getPlayerScore());
        while (!checkMovementIsZero()){
            System.out.println(" ");
            System.out.println(playerName + " at position X: " + playerPosX + " Y: " + playerPosY );
            System.out.println("dest x: " + destination.destinationX + " dest y: " + destination.destinationY);
            System.out.println("available movement left: " + getAvailableMovement());
            String input = IO.readln("choose movement! Available is up, down, left, right, auto");
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
                }case "auto" -> autoMove();
                 default -> System.out.println("Invalid input!");
            }
            if (checkIfPlayerIsAtDestination()){
                increaseScore();
            }

        }
        System.out.println("End of "+ playerName + " turn");
        System.out.println("- - - - - - - - - -");
        checkIfPlayerHasPenalties();
        checkIfPlayerHasBonus();
        playerTurnCount++;
    }

    public void chooseTransportation(List<Transport> transport) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        for (int i = 0; i < transport.size(); i++){
            System.out.println("Nr " + (i+1) + ": " + transport.get(i).getType() + ". Costs: " + transport.get(i).getCostPerMove());
        }
        System.out.println("Nr 4: Player walks");
        while (true){
            System.out.println("Player has " + getCredits() + " credits");
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
                    if (checkIfPlayerHasEnoughCredits(transport.getFirst().getCostPerMove().intValue())){
                        this.selectedTransportDice = transport.getFirst().getDiceCount();
                        removeCredits(transport.getFirst().getCostPerMove().intValue());
                        return;
                    } else {
                        System.out.println("Not enough credits");
                    }
                }
                case 2 -> {

                    if (checkIfPlayerHasEnoughCredits(transport.get(1).getCostPerMove().intValue())){
                        this.selectedTransportDice = transport.get(1).getDiceCount();
                        removeCredits(transport.get(1).getCostPerMove().intValue());
                        return;
                    } else {
                        System.out.println("Not enough credits");
                    }
                }
                case 3 -> {
                    if (checkIfPlayerHasEnoughCredits(transport.get(2).getCostPerMove().intValue())){
                        this.selectedTransportDice = transport.get(2).getDiceCount();
                        removeCredits(transport.get(2).getCostPerMove().intValue());
                        return;
                    } else {
                        System.out.println("Not enough credits");
                    }
                } case 4 -> {
                    System.out.println("Player walks");
                    this.selectedTransportDice = (int) ((4 * Math.random()+1));
                    return;
                }
                default -> System.out.println("Invalid input, needs to be a number");
            }
        }
    }

    //Dice roll method
    public int rollDice(int amount) {
        int tempRoll;
        int roll = 0;
        for (int i = 0; i < amount; i++){
            tempRoll = (int) ((dice * Math.random()+1));
            System.out.println("Rolled a " + tempRoll);
            roll += tempRoll;
        }
        System.out.println("Total amount is " + roll);
        return roll;
    }
}




