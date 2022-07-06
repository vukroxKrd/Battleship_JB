package battleship.vessels;

import java.util.Objects;

public abstract class Coordinate implements Comparable<Coordinate> {

    private char letter;
    private int number;

    public Coordinate() {
    }

    public Coordinate(char letter, int number) {
        this.letter = letter;
        this.number = number;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;
        Coordinate that = (Coordinate) o;
        return this.getLetter() == that.getLetter() && this.getNumber() == that.getNumber();
    }

    @Override
    public int hashCode() {
        return Objects.hash(letter, number);
    }

    @Override
    public int compareTo(Coordinate o) {
        int result;
        result = Character.compare(this.getLetter(), o.getLetter());
        if (result != 0) return result;
        result = Integer.compare(this.getNumber(), o.getNumber());
        return result;
    }

    @Override
    public String toString() {
        return "Coordinate{" +
                "letter=" + letter +
                ", number=" + number +
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
}
