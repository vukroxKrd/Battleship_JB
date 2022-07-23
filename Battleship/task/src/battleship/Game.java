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

        do {
            takeActionOnOpponent(player, opponent);
            takeActionOnOpponent(opponent, player);
        } while (continuePlaying(player, opponent));
        System.out.println("You sank the last ship. You won. Congratulations!");
    }

    //Step 1 of the game.
    // Players have to place ships on the map. 2-dimensional array is heavily used. While doing so 2 collections are created and filed:
    //1.Coordinates,
    //2.Enclosed fields.
    //This data is used later to facilitate the game process.
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

    //This method is determining when it is the time to stop playing.
    public boolean continuePlaying(Player player, Player opponent) {
        boolean someShipAfloat = true;

        boolean aPlayerShipAfloat = player.getPlayerFleet().getShips().stream().anyMatch(Ship::isAfloat);
        boolean anOpponentShipAfloat = opponent.getPlayerFleet().getShips().stream().anyMatch(Ship::isAfloat);

        if (!aPlayerShipAfloat || !anOpponentShipAfloat) {
            someShipAfloat = false;
        }

        return someShipAfloat;
    }

    // Step 2 main method. This is where the shooting takes place. Every player has 2 fields.
    // The player cannot see the field of his opponent due to the fog of war,
    // but he can keep track of the shots made by himself and by his opponent.
    public void takeActionOnOpponent(Player player, Player opponent) {

        opponent.getField().printBattleField(opponent.getField().prepareBattleFieldWithTheFogOfWar(player));
        player.getField().printBattleField();

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

        //This block of code is executed when the player hits a ship.
        // It marks shots and coordinates as 'hit' but also flags ships as destroyed
        //when they are not afloat.
        anyCoordinate.ifPresentOrElse(
                (coordinate) -> {
                    coordinate.setHit(true);
                    shot.setStrike(true);

                    allShots.values().stream().filter(shot::equals).forEach(shot1 -> shot1.setStrike(true));
                    player.setShots(allShots);

                    bField[NavigationUtils.letterNumberMap.get(coordinate.getLetter())][coordinate.getNumber()] = String.valueOf('X');

                    opponent.getField().setBattleField(bField);

                    //checking the ship is afloat below. if not setting a boolean flag to know when there are no ships afloat to stop playing.
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
                //This block of code is executed when the player didn't hit any ships.
                () -> {
                    bField[NavigationUtils.letterNumberMap.get(shot.getLetter())][shot.getNumber()] = String.valueOf('M');
                    opponent.getField().setBattleField(bField);
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
