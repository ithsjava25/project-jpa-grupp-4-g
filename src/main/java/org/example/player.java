package org.example;

abstract class player {
    String playerName;
    int playerScore = 0;
    long credits = 1000;

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    public String getPlayerName() {
        return playerName;
    }

    public void setPlayerCredits(long credits) {
        this.credits = credits;
    }

    public long getCredits() {
        return credits;
    }

    public void payCredits(long credits) {
        this.credits = this.credits - credits;
    }

    void checkIfPlayerHasPenalties(){
        if (Math.random() < 0.10){
            System.out.println("You got the penalty!");
           getPenalty();
        }
    }

    public void getPenalty() {
        int getPenaltyNumber = (int) Math.random()*5;
        switch (getPenaltyNumber) {
            case 1 -> {
                System.out.println(" ");
                System.out.println("You fell and bruised, pay some fees 50 credits");
                payCredits(50);
            } case 2 -> {
                System.out.println(" ");
                System.out.println("You partied to hard last night, lost 100 credits");
                payCredits(100);
            } case 3 -> {
                System.out.println(" ");
                System.out.println("You got scammed by a local, what a shame");
                payCredits(150);
            } case 4 -> {
                System.out.println(" ");
                System.out.println("You're transport broke, need a mechanic");
                payCredits(200);
            } case 5 -> {
                System.out.println(" ");
                System.out.println("You have a big phone bill, dont call that much");
                payCredits(250);
            } default -> {
                System.out.println(" ");
                System.out.println("no penalties");
            }
        }

    }

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
