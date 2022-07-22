package battleship.player;

import battleship.utils.CoordinatesRequestor;
import battleship.vessels.Coordinate;
import battleship.vessels.CoordinateUnit;
import battleship.vessels.Ship;

import java.util.*;
import java.util.stream.Collectors;

public class Player {

    static int instanceCounter = 0;

    private String name;
    private int playerNumber;
    private Map<Integer, Shot> shots;
    private Map<String, Integer> record;
    private Fleet playerFleet;

    public Player(String name) {
        instanceCounter++;

        this.name = name;
        this.shots = new HashMap<>();
        this.record = new HashMap<>();
        this.playerFleet = new Fleet();
        this.playerNumber = instanceCounter;
    }

    public Shot produceShot() {
        String coordinates = CoordinatesRequestor.requestUserInput();

        OptionalInt letter = coordinates.codePoints()
                .filter(Character::isLetter)
                .findFirst();
        char l = (char) letter.getAsInt();
        l = Character.toUpperCase(l);

        List<Integer> number = CoordinatesRequestor.findNumbers(coordinates);
        int i = number.get(0);

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

    public int getPlayerNumber() {
        return playerNumber;
    }

    public class Fleet {

        private boolean shipsLeft = true;

        private List<Ship> ships = new ArrayList<>();

        public List<Ship> getShips() {
            return ships;
        }

        public void setShips(List<Ship> ships) {
            this.ships = ships;
        }

        public boolean isShipsLeft() {
            return shipsLeft;
        }

        public void setShipsLeft(boolean shipsLeft) {
            this.shipsLeft = shipsLeft;
        }

        public boolean areAnyShipsLeft() {
            boolean result = this.ships.stream().anyMatch(Ship::isAfloat);
            setShipsLeft(result);
            return result;
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

