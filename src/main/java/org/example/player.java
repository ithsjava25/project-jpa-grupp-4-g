package org.example;

abstract class Player {
    String playerName;
    private int playerScore = 0;
    private int credits = 1000;

    //set the player name
    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    public String getPlayerName() {
        return playerName;
    }

    public void setPlayerCredits(int credits) {
        if (credits < 0){
            this.credits = credits;
        } else {
            this.credits = credits;
        }

    }

    public long getCredits() {
        return credits;
    }

    //remove credits from a player bank
    public void removeCredits(int credits) {
        if (this.credits - credits < 0) {
            this.credits = 0;
        }else {
            this.credits = this.credits - credits;
        }
    }

    //add credits to player bank
    public void addCredits(int credits) {
        this.credits = this.credits + credits;
    }

    //Make a penalties check for the round. A chance under 10 percent for a player to get a penalty.
    void checkIfPlayerHasPenalties(){
        if (Math.random() < 0.10){
            System.out.println("You got the penalty!");
           getPenalty();
        }
    }

    /* if a player gets a penalty, get a random number between 0 - 5, choose a penlty from the switch.*/
    //TODO fix credits for correct value
    public void getPenalty() {
        int getPenaltyNumber = (int) (5*Math.random()+1);
        switch (getPenaltyNumber) {
            case 1 -> {
                System.out.println(" ");
                System.out.println("You fell and bruised, pay some fees 50 credits");
                removeCredits(50);
            } case 2 -> {
                System.out.println(" ");
                System.out.println("You partied to hard last night, lost 100 credits");
                removeCredits(100);
            } case 3 -> {
                System.out.println(" ");
                System.out.println("You got scammed by a local, what a shame");
                removeCredits(150);
            } case 4 -> {
                System.out.println(" ");
                System.out.println("You're transport broke, need a mechanic");
                removeCredits(200);
            } case 5 -> {
                System.out.println(" ");
                System.out.println("You have a big phone bill, dont call that much");
                removeCredits(250);
            } default -> {
                System.out.println(" ");
                System.out.println("no penalties");
            }
        }

    }

    /*
    * Scoring for a player. Players start with zero and has a win state when the score reaches 5. */
    public void increaseScore() {
        playerScore++;
    }

    public int getPlayerScore() {
        return playerScore;
    }

    public boolean checkScore() {
        if (playerScore == 5) {
            return true;
        }
        return false;
    }
}
