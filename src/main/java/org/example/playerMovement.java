package org.example;

//@Todo player movement
//with continents, countries to cites
//arrays efter uppdrag

import java.util.Objects;

public class playerMovement {
   void main(){
       String[] countries = {"Start",
           "Stockholm",
           "Copenhagen",
           "Oslo",
           "Helsinki",
           "Berlin",
           "Paris",
           "Luxembourg",
           "Lichenstein",
           "Madrid"};

       String[] playerPos = new String[countries.length];
       playerPos[0] = "Player 1";
       boolean endBoard = true;
       while(endBoard){
           IO.readln("Play by enter");
           if (playerPos[playerPos.length-1] == null){
               playerPos = playRound(playerPos, countries);
           } else {
               endBoard = false;
               System.out.println("Player 1 at end of board");
           }
       }
   }

    private String[] playRound(String[] playerPos, String[] countries) {
        playerPos = movePlayer(playerPos);
        for (int i = 1; i < playerPos.length; i++){
            if (playerPos[i] != null){
                System.out.println("Player 1 on position " + i);
                System.out.println(countries[i]);
            }
        }
        return playerPos;
    }

    public String[] movePlayer(String[] playerPos){
       String[] temp = new String[playerPos.length];
       int playerPosition = 0;
       for (int i = 0; i < playerPos.length; i++){
           if (playerPos[i] != null){
               playerPosition = i;
           }
       }
       int newPosition = playerPosition + rollDice(4);
       if (newPosition < 0 || newPosition < playerPos.length){
           temp[newPosition] = "player 1";
       } else {
           temp[playerPos.length - 1] = "player 1";
       }
        return temp;
   }

    public int rollDice(int dice) {
        int roll = (int) ((dice * Math.random()+1));
        System.out.println("Rolled " + roll);
        return roll;
    }
}

