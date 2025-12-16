package org.example;

//@Todo player movement
//with continents, countries to cites

public class playerMovement extends player{

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


    public String getPlayerName() {
        return this.playerName;
    }

   void play(){
       String[] playerPos = new String[board.length];
       playerPos[0] = playerName;
       boolean endBoard = true;
       while(endBoard){
           IO.readln("Play by enter");
           if (playerPos[playerPos.length-1] == null){
               playerPos = playRound(playerPos, board);
           } else {
               endBoard = false;
               System.out.println(playerName + " at end of board");
               playerPos = new String[board.length];
           }
       }
   }

    private String[] playRound(String[] playerPos, String[] board) {
        playerPos = movePlayer(playerPos);
        for (int i = 1; i < playerPos.length; i++){
            if (playerPos[i] != null){
                System.out.println(playerName + " on position " + i);
                System.out.println(board[i]);
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

