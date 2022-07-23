package battleship.player;

import battleship.vessels.Coordinate;


/**
 * This class represents coordinates which are produced by a player on the stage when the ships are being shot.
 *
 * "strike" field was included mainly for reference and statistics in case it would have been necessary to produce some kind of
 * effectiveness statistics.
 * */
public class Shot extends Coordinate {

    private char letter;
    private int number;
    private boolean strike;

    public Shot(char letter, int number) {
        this.letter=letter;
        this.number=number;
    }

    @Override
    public String toString() {
        return "Shot{" +
                "letter=" + letter +
                ", number=" + number +
                ", strike=" + strike +
                '}';
    }

    @Override
    public char getLetter() {
        return letter;
    }

    @Override
    public void setLetter(char letter) {
        this.letter = letter;
    }

    @Override
    public int getNumber() {
        return number;
    }

    @Override
    public void setNumber(int number) {
        this.number = number;
    }

    public boolean isStroke() {
        return strike;
    }

    public void setStrike(boolean strike) {
        this.strike = strike;
    }
}
