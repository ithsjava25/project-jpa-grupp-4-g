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

