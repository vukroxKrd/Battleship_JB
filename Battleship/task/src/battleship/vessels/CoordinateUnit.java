package battleship.vessels;

public class CoordinateUnit extends Coordinate {

    private char letter;
    private int number;
    private boolean hit;

    public CoordinateUnit(char letter, int number) {
        this.letter=letter;
        this.number=number;
        this.hit = false;
    }

    @Override
    public String toString() {
        return "CoordinateUnit{" +
                "letter=" + letter +
                ", number=" + number +
                ", hit=" + hit +
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

    public boolean isHit() {
        return hit;
    }

    public void setHit(boolean hit) {
        this.hit = hit;
    }
}
