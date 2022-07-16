package battleship.vessels;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public abstract class Ship {

    private String name;
    private int productionSize;
    private VesselType type;
    private int size;
    private int rearLetter;
    private int rearNumber;
    private int foreLetter;
    private int foreNumber;
    private boolean afloat;

    private Map<Integer, CoordinateUnit> coordinates = new HashMap<>();
    private Map<Integer, EnclosedField> enclosedFields = new HashMap<>();

    public Ship() {
    }

    public Ship(int rearLetter, int rearNumber, int foreLetter, int foreNumber) {

        int shipSize;
        if (foreNumber - rearNumber != 0) {
            shipSize = (foreNumber + 1) - rearNumber;
        } else {
            shipSize = (foreLetter + 1) - rearLetter;
        }
        this.size = shipSize;
        this.rearLetter = rearLetter;
        this.rearNumber = rearNumber;
        this.foreLetter = foreLetter;
        this.foreNumber = foreNumber;
        this.afloat = true;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Ship)) return false;
        Ship ship = (Ship) o;
        return getProductionSize() == ship.getProductionSize()
                && getRearLetter() == ship.getRearLetter()
                && getRearNumber() == ship.getRearNumber()
                && getForeLetter() == ship.getForeLetter()
                && getForeNumber() == ship.getForeNumber()
                && getName().equals(ship.getName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getName(), getProductionSize(), getRearLetter(), getRearNumber(), getForeLetter(), getForeNumber());
    }

    public void changeHitToTrueWhenTheShipWasShot(CoordinateUnit coordinate) {

        this.getCoordinates().values().stream()
                .filter(coordinate::equals)
                .forEach(coordinateUnit -> coordinateUnit.setHit(true));
    }

    public boolean checkShipIsStillAfloat() {
        boolean shipIsSunk = this.getCoordinates().values().stream().allMatch(CoordinateUnit::isHit);
        if (shipIsSunk) {
            afloat = false;
        }
        return afloat;
    }

    public Map<Integer, EnclosedField> getEnclosedFields() {
        return enclosedFields;
    }

    public void setEnclosedFields(Map<Integer, EnclosedField> enclosedFields) {
        this.enclosedFields = enclosedFields;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public boolean isAfloat() {
        return afloat;
    }

    public void setAfloat(boolean afloat) {
        this.afloat = afloat;
    }

    public int getRearLetter() {
        return rearLetter;
    }

    public void setRearLetter(int rearLetter) {
        this.rearLetter = rearLetter;
    }

    public int getRearNumber() {
        return rearNumber;
    }

    public void setRearNumber(int rearNumber) {
        this.rearNumber = rearNumber;
    }

    public int getForeLetter() {
        return foreLetter;
    }

    public void setForeLetter(int foreLetter) {
        this.foreLetter = foreLetter;
    }

    public int getForeNumber() {
        return foreNumber;
    }

    public void setForeNumber(int foreNumber) {
        this.foreNumber = foreNumber;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getProductionSize() {
        return productionSize;
    }

    public VesselType getType() {
        return type;
    }

    public Map<Integer, CoordinateUnit> getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(Map<Integer, CoordinateUnit> coordinates) {
        this.coordinates = coordinates;
    }
}
