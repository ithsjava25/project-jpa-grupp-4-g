package org.example;

//@Todo player movement
//with continents, countries to cites
//arrays efter uppdrag

import java.util.Objects;

public class playerMovement {
   void main(){
       String[] countries = {"Start", "Stockholm", "Copenhagen", "Oslo", "Helsinki", "Berlin", "Paris", "Luxembourg", "Lichenstein", "Madrid"};

       String[] playerPos = new String[countries.length];
       playerPos[0] = "Player 1";
       int endBoard = 0;
       playerPos = movePlayer(playerPos);
       for (int i = 1; i < playerPos.length; i++){
           if (playerPos[i] != null){
               System.out.println("Player 1 on position " + (i+1));
               System.out.println(countries[i]);
               endBoard = i;
           }
        }
   }

    public String[] movePlayer(String[] playerPos){
       String[] temp = new String[playerPos.length];
       int playerPosition = 0;
       for (int i = 0; i < playerPos.length; i++){
           if (Objects.equals(playerPos[i], "Player 1")){
               playerPosition = i;
           }
       }

       temp[playerPosition + rollDice(4)] = "player 1";

       return playerPos = temp;
   }

    public int rollDice(int dice) {
        return (int) ((dice * Math.random()+1));
    }
}

