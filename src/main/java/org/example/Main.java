package org.example;

public class Main {
    public static void main(String[] args) throws Exception {
        if (args.length > 0 && args[0].equalsIgnoreCase("cli")) {
            GameConfig.MODE = GameMode.CLI;
            App.main(new String[0]);
        } else {
            GameConfig.MODE = GameMode.GUI;
            TravelGame.main(args);
        }
    }
}
