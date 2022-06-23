package battleship.vessels;

import battleship.Field;

import java.util.HashMap;
import java.util.Map;

import static battleship.Field.LOWER_BOUNDARY;
import static battleship.Field.UPPER_BOUNDARY;

public abstract class Ship {
    private int size;
    private String[] rear;
    private String[] fore;
    private boolean afloat;
    private Map<Character, Integer> enclosedFields;

    public Ship(String[] rear, String[] fore) {
        this.rear = rear;
        this.fore = fore;
        this.afloat = true;
    }

    public Map<Character, Integer> getEnclosedFields() {
        return enclosedFields;
    }

    public void setEnclosedFields(Map<Character, Integer> enclosedFields) {
        this.enclosedFields = enclosedFields;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public String[] getRear() {
        return rear;
    }

    public void setRear(String letter, String number) {
        this.rear = new String[]{letter, number};
    }

    public String[] getFore() {
        return fore;
    }

    public void setFore(String letter, String number) {
        this.fore = new String[]{letter, number};
    }

    public boolean isAfloat() {
        return afloat;
    }

    public void setAfloat(boolean afloat) {
        this.afloat = afloat;
    }

}
