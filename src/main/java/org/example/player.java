package org.example;

abstract class Player {
    String playerName;
    private int playerScore = 0;
    int credits = 10000;

    //set the player name
    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    public String getPlayerName() {
        return playerName;
    }

    public void setPlayerCredits(int credits) {
        if (credits < 0){
            System.out.println("Invalid, credits cannot be under zero!");
            this.credits = 0;
        } else {
            this.credits = credits;
        }
    }

    public int getCredits() {
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
        if(credits < 0){
            System.out.println("Invalid, credits cannot be under zero!");
            return;
        }
        this.credits = this.credits + credits;
    }

    public boolean checkIfPlayerHasEnoughCredits(int credits) {
        if(this.credits < credits){
            System.out.println("Not enough credits for option");
            return false;
        }
        return true;
    }

    //Make a penalties check for the round. A chance under 5 percent for a player to get a penalty.
    void checkIfPlayerHasPenalties(){
        if (Math.random() < 0.05){
            System.out.println("You got the penalty!");
           getPenalty();
        }
    }

    /* if a player gets a penalty, get a random number between 0 - 5, choose a penalty from the switch.*/
    public void getPenalty() {
        int getPenaltyNumber = (int) (6*Math.random()+1);
        switch (getPenaltyNumber) {
            case 1 -> {
                System.out.println(" ");
                System.out.println("You fell and bruised, pay some fees 50 credits");
                removeCredits(50);
            } case 2 -> {
                System.out.println(" ");
                System.out.println("You partied too hard last night, lost 100 credits");
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
            } case 6 -> {
                System.out.println(" ");
                System.out.println("Really.. the Ritz hotel");
                removeCredits(500);
            }default -> {
                System.out.println(" ");
                System.out.println("no penalties");
            }
        }
    }

    void checkIfPlayerHasBonus(){
        if (Math.random() < 0.10){
            System.out.println("You got the penalty!");
            getBonus();
        }
    }

    public void getBonus() {
        int getPenaltyNumber = (int) (6*Math.random()+1);
        switch (getPenaltyNumber) {
            case 1 -> {
                System.out.println(" ");
                System.out.println("found some spare change");
                addCredits(50);
            } case 2 -> {
                System.out.println(" ");
                System.out.println("helped an old woman across the street");
                addCredits(100);
            } case 3 -> {
                System.out.println(" ");
                System.out.println("got a day job");
                addCredits(250);
            } case 4 -> {
                System.out.println(" ");
                System.out.println("you're parents sent you some money");
                addCredits(400);
            } case 5 -> {
                System.out.println(" ");
                System.out.println("Happy birthday");
                addCredits(500);
            } case 6 -> {
                System.out.println(" ");
                System.out.println("won the lottery");
                addCredits(1000);
            }default -> {
                System.out.println(" ");
                System.out.println("no bonus");
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
        if (playerScore == 1) {
            return true;
        }
        return false;
    }
}
