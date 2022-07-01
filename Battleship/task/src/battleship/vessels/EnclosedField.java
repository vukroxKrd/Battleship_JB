package battleship.vessels;

public class EnclosedField extends Coordinate {

    private char letter;
    private int number;

    public EnclosedField(char letter, int number) {
        this.letter = letter;
        this.number = number;
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

    @Override
    public String toString() {
        return "EnclosedField{" +
                "letter=" + letter +
                ", number=" + number +
                '}';
    }
}
