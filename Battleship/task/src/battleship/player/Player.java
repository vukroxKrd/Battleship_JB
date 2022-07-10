package battleship.player;

import battleship.Field;
import battleship.utils.CoordinatesRequestor;
import battleship.vessels.Coordinate;
import battleship.vessels.CoordinateUnit;
import battleship.vessels.Ship;

import java.util.*;
import java.util.stream.Collectors;

public class Player {

    private String name;
    private Map<Integer, Shot> shots;
    private Map<String, Integer> record;
    private Fleet playerFleet;

    public Player(String name) {
        this.name = name;
        this.shots = new HashMap<>();
        this.record = new HashMap<>();
        this.playerFleet = new Fleet();
    }

    public Shot produceShot() {
        System.out.println("Take a shot!");

        String coordinates = CoordinatesRequestor.requestUserInput();

        OptionalInt letter = coordinates.codePoints()
                .filter(Character::isLetter)
                .findFirst();
        char l = (char) letter.getAsInt();
        l = Character.toUpperCase(l);

        OptionalInt number = coordinates.codePoints()
                .filter(Character::isDigit)
                .findFirst();
        int i = Character.getNumericValue(number.getAsInt());

        Shot shot = new Shot(l, i);

        this.getShots().put(getShots().size(), shot);

        return shot;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Map<Integer, Shot> getShots() {
        return shots;
    }

    public void setShots(Map<Integer, Shot> shots) {
        this.shots = shots;
    }

    public Map<String, Integer> getRecord() {
        return record;
    }

    public void setRecord(Map<String, Integer> record) {
        this.record = record;
    }

    public Fleet getPlayerFleet() {
        return playerFleet;
    }

    public void setPlayerFleet(Fleet fleet) {
        this.playerFleet = fleet;
    }

    public class Fleet {
        private List<Ship> ships = new ArrayList<>();

        public List<Ship> getShips() {
            return ships;
        }

        public void setShips(List<Ship> ships) {
            this.ships = ships;
        }

        public Optional<Ship> findShipByCoordinate(Coordinate coordinate) {

            Optional<Ship> ship1 = ships.stream()
                    .filter(ship -> ship.getCoordinates().values().stream()
                            .anyMatch(coordinateUnit ->
                                    coordinateUnit.equals(coordinate)))
                    .findAny();
            return ship1;
        }

        private List<CoordinateUnit> fieldsWhichWereHit() {

            return ships.stream()
                    .flatMap(ship -> ship.getCoordinates().values()
                            .stream())
                    .filter(CoordinateUnit::isHit)
                    .collect(Collectors.toList());
        }
    }
}

