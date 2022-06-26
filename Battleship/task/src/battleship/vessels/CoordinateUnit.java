package battleship.vessels;

public class CoordinateUnit {

    private char letter;
    private int number;
    private boolean wasHit;

    public CoordinateUnit(char letter, int number) {
        this.letter = letter;
        this.number = number;
        this.wasHit = false;
    }

    @Override
    public String toString() {
        return "CoordinateUnit{" +
                "letter=" + letter +
                ", number=" + number +
                ", wasHit=" + wasHit +
                '}';
    }

    public char getLetter() {
        return letter;
    }

    public void setLetter(char letter) {
        this.letter = letter;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public boolean isWasHit() {
        return wasHit;
    }

    public void setWasHit(boolean wasHit) {
        this.wasHit = wasHit;
    }
}
