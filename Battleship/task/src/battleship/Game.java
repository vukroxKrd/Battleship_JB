package battleship;

import battleship.exceptions.IncorrectShipSizeException;
import battleship.exceptions.ShipCoordinatesOutTheBoardException;
import battleship.exceptions.ShipPlacedTooCloseToOtherShipException;
import battleship.exceptions.WrongShipLocationException;
import battleship.player.Player;
import battleship.player.Shot;
import battleship.utils.CoordinatesRequestor;
import battleship.utils.NavigationUtils;
import battleship.vessels.Coordinate;
import battleship.vessels.CoordinateUnit;
import battleship.vessels.Ship;
import battleship.vessels.ShipFactory;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicBoolean;

import static battleship.utils.CoordinatesRequestor.passMove;

public class Game {

    private Player playerOne;
    private Player playerTwo;

    public Game(Player playerOne, Player playerTwo) {
        this.playerOne = playerOne;
        this.playerTwo = playerTwo;
    }

    public void play(Player playerOne, Player playerTwo) {
        var player = placeAllShipsOnTheMap(playerOne, playerOne.getField());
        var opponent = placeAllShipsOnTheMap(playerTwo, playerTwo.getField());
//        System.out.println("The game starts!");
        do {
            takeActionOnOpponent(player, opponent);
            takeActionOnOpponent(opponent, player);
        } while (continuePlaying(player, opponent));
        System.out.println("You sank the last ship. You won. Congratulations!");
    }

    public Player placeAllShipsOnTheMap(Player player, Field field) {

        System.out.println("Player " + player.getPlayerNumber() + ", place your ships on the game field" + System.lineSeparator());

        var fleet = player.getPlayerFleet();

        field.printBattleField();

        for (Ship draft : Field.draftFleet) {
            Ship placedShip = null;
            boolean repeat = false;

            do {
                try {
                    List<Integer> coordinates = CoordinatesRequestor.requestCoordinates(draft, repeat);
                    repeat = false;
                    Ship ship = ShipFactory.createShip(draft.getType(), coordinates);
                    placedShip = field.placeShipOnMap(ship, field);
                } catch (ShipCoordinatesOutTheBoardException | IncorrectShipSizeException | WrongShipLocationException e) {
                    repeat = true;
                    System.out.println(e.getMessage());
                    continue;
                }
                if (fleet.getShips().isEmpty()) {
                    fleet.getShips().add(placedShip);
                } else {
                    try {
                        for (Ship nextShip : fleet.getShips()) {
                            for (Coordinate enclosedOfAlreadyAddedShip : nextShip.getEnclosedFields().values()) {
                                for (Coordinate coordinatesOfPlacedShips : nextShip.getCoordinates().values()) {
                                    for (Coordinate coordinateUnit : placedShip.getCoordinates().values()) {
                                        if (coordinateUnit.equals(enclosedOfAlreadyAddedShip) || coordinateUnit.equals(coordinatesOfPlacedShips)) {
                                            String[][] battleField = field.getBattleField();
                                            for (Coordinate coordinate : placedShip.getCoordinates().values()) {
                                                battleField[NavigationUtils.letterNumberMap.get(coordinate.getLetter())][coordinate.getNumber()] = String.valueOf('~');
                                            }
                                            field.setBattleField(battleField);
                                            throw new ShipPlacedTooCloseToOtherShipException("\nError! You placed it too close to another one. Try again:\n");
                                        }
                                    }
                                }
                            }
                        }
                    } catch (ShipPlacedTooCloseToOtherShipException e) {
                        repeat = true;
                        System.out.println(e.getMessage());
                    }
                    if (!repeat) {
                        fleet.getShips().add(placedShip);
                    }
                }
            }
            while (repeat);

            player.setPlayerFleet(fleet);
            field.printBattleField();
        }

        passMove();
        return player;
    }

    public boolean continuePlaying(Player player, Player opponent) {
        boolean someShipAfloat = true;

        boolean aPlayerShipAfloat = player.getPlayerFleet().getShips().stream().anyMatch(Ship::isAfloat);
        boolean anOpponentShipAfloat = opponent.getPlayerFleet().getShips().stream().anyMatch(Ship::isAfloat);

        if (!aPlayerShipAfloat || !anOpponentShipAfloat) {
            someShipAfloat = false;
        }

        return someShipAfloat;
    }

    public void takeActionOnOpponent(Player player, Player opponent) {

        opponent.getField().printBattleField(opponent.getField().prepareBattleFieldWithTheFogOfWar(player));
        player.getField().printBattleField();
//            System.out.println("\nTake a shot!");


        Shot shot = player.produceShot();
        Map<Integer, Shot> allShots = player.getShots();

        Player.Fleet fleetOfOpponent = opponent.getPlayerFleet();
        List<Ship> shipsOfOpponent = fleetOfOpponent.getShips();
        String[][] bField = opponent.getField().getBattleField();

        Optional<CoordinateUnit> anyCoordinate = shipsOfOpponent.stream()
                .map(Ship::getCoordinates)
                .map(Map::values)
                .flatMap(Collection::stream)
                .filter(coordinate -> {
                    Coordinate c1 = coordinate;
                    Coordinate c2 = shot;

                    return c1.equals(c2);
                })
                .findAny();

        anyCoordinate.ifPresentOrElse(
                (coordinate) -> {
                    coordinate.setHit(true);
                    shot.setStrike(true);

                    allShots.values().stream().filter(shot::equals).forEach(shot1 -> shot1.setStrike(true));
                    player.setShots(allShots);

                    bField[NavigationUtils.letterNumberMap.get(coordinate.getLetter())][coordinate.getNumber()] = String.valueOf('X');

                    opponent.getField().setBattleField(bField);

//                    opponent.getField().printBattleField(opponent.getField().prepareBattleFieldWithTheFogOfWar(player));
//                    player.getField().printBattleField();

                    AtomicBoolean afloat = new AtomicBoolean(true);
                    fleetOfOpponent
                            .findShipByCoordinate(coordinate)
                            .ifPresent(ship -> {
                                ship.changeHitToTrueWhenTheShipWasShot(coordinate);
                                if (ship.isAfloat()) {
                                    afloat.set(ship.checkShipIsStillAfloat());
                                }
                            });
                    opponent.setPlayerFleet(fleetOfOpponent);

                    if (!afloat.get() && fleetOfOpponent.areAnyShipsLeft()) {
                        System.out.println("You sank a ship! Specify a new target:");
                        passMove();
                    } else if (afloat.get() && fleetOfOpponent.areAnyShipsLeft()) {
                        System.out.print("You hit a ship!");
                        passMove();
                    }
                },
                () -> {
                    bField[NavigationUtils.letterNumberMap.get(shot.getLetter())][shot.getNumber()] = String.valueOf('M');
                    opponent.getField().setBattleField(bField);

//                    opponent.getField().printBattleField(opponent.getField().prepareBattleFieldWithTheFogOfWar(player));
//                    player.getField().printBattleField();
                    System.out.print("You missed!");
                    passMove();
                }
        );
    }

    public Player getPlayerOne() {
        return playerOne;
    }

    public void setPlayerOne(Player playerOne) {
        this.playerOne = playerOne;
    }

    public Player getPlayerTwo() {
        return playerTwo;
    }

    public void setPlayerTwo(Player playerTwo) {
        this.playerTwo = playerTwo;
    }
}
