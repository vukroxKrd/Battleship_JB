package battleship;

import battleship.player.Player;

public class Main {

    public static void main(String[] args) {

        Player playerOne = new Player("Slava");
        Player playerTwo = new Player("Kate");

        Game game = new Game(playerOne, playerTwo);
        game.play(playerOne, playerTwo);
    }
}
