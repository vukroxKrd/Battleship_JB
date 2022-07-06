package battleship;

import battleship.player.Player;

public class Main {

    public static void main(String[] args) {

        Field field = Field.getInstance();
        Player playerOne = new Player("Slava");
        Player playerTwo = new Player("Kate");

        Game game = new Game(playerOne, playerTwo, field);
        game.play(field, playerOne, playerTwo);
    }
}
