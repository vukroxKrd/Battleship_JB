package battleship;

import battleship.exceptions.IncorrectShipSizeException;
import battleship.exceptions.ShipCoordinatesOutTheBoardException;
import battleship.exceptions.WrongShipLocationException;
import battleship.utils.NavigationUtils;
import battleship.vessels.CoordinateUnit;
import battleship.vessels.EnclosedField;
import battleship.vessels.Ship;

import java.util.HashMap;
import java.util.Map;

import static battleship.utils.NavigationUtils.letterNumberMap;
import static battleship.utils.NavigationUtils.numberLetterMap;

public class Field {

    private String[][] battleField;
    private static Field instance;
    public static final int LOWER_BOUNDARY = 1;
    public static final int UPPER_BOUNDARY = 11;

    private Field() {
    }

    public static Field getInstance() {
        if (instance == null) {
            instance = new Field();
            instance.prepareBattleField();
        }
        return instance;
    }

    private void prepareBattleField() {
        this.battleField = new String[11][11];
        battleField[0][0] = " ";
        battleField[0][1] = "1";
        battleField[0][2] = "2";
        battleField[0][3] = "3";
        battleField[0][4] = "4";
        battleField[0][5] = "5";
        battleField[0][6] = "6";
        battleField[0][7] = "7";
        battleField[0][8] = "8";
        battleField[0][9] = "9";
        battleField[0][10] = "10";
        battleField[1][0] = "A";
        battleField[2][0] = "B";
        battleField[3][0] = "C";
        battleField[4][0] = "D";
        battleField[5][0] = "E";
        battleField[6][0] = "F";
        battleField[7][0] = "G";
        battleField[8][0] = "H";
        battleField[9][0] = "I";
        battleField[10][0] = "J";

        for (int i = 1; i < battleField.length; i++) {
            for (int j = 1; j < battleField[i].length; j++) {
                battleField[i][j] = String.valueOf('~');
            }
        }
    }

    public void printBattleField() {
        for (String[] strings : battleField) {
            for (int j = 0; j < strings.length; j++) {
                System.out.print(strings[j] + " ");
            }
            System.out.println();
        }
    }

    public String[][] getBattleField() {
        return battleField;
    }

    public void setBattleField(String[][] battleField) {
        this.battleField = battleField;
    }

    public Ship placeShipOnMap(Ship ship) throws ShipCoordinatesOutTheBoardException, IncorrectShipSizeException, WrongShipLocationException {
        //can potentially break multithreaded code;

        int enclosedFieldCounter = 1;
        int coordinatesCounter = 1;

        Map<Integer, EnclosedField> enclosedFields = ship.getEnclosedFields();
        Map<Integer, CoordinateUnit> coordinates = ship.getCoordinates();

        String[][] field = Field.getInstance().getBattleField();

        boolean continueProcessing = true;

        validateShipPosition(ship);

        //For horizontal ships they have the same letter in the coordinates
        if (ship.getRearLetter() == ship.getForeLetter()) {
            for (int i = 1; i < field.length && continueProcessing; i++) {
                for (int j = 1; j < field[i].length; j++) {
                    if (i != ship.getRearLetter()) {
                        break;
                    }

                    if (ship.getSize() == 0) {
                        ship.setAfloat(false);
                        continueProcessing = false;
                    }
                    // the ship is not touching borders
//                    if (i > 1 && j > 1 && i < field.length - 1 && j < field.length - 1) {
                    if (i == ship.getRearLetter() && j >= ship.getRearNumber() && j <= ship.getForeNumber() && ship.getRearLetter() != 1 && ship.getRearLetter() != 10) {
                        if (j == ship.getRearNumber() && ship.getRearNumber() > 1) {
                            enclosedFields.put(enclosedFieldCounter++, new EnclosedField(numberLetterMap.get(i), (j - 1)));
                            enclosedFields.put(enclosedFieldCounter++, new EnclosedField(numberLetterMap.get(i - 1), (j - 1)));
                            enclosedFields.put(enclosedFieldCounter++, new EnclosedField(numberLetterMap.get(i + 1), (j - 1)));

//                            field[i][j - 1] = ".";
//                            field[i - 1][j - 1] = ".";
//                            field[i + 1][j - 1] = ".";
                        }
                        if (j == ship.getForeNumber() && ship.getForeNumber() < 10 && ship.getForeLetter() < 10) {

                            enclosedFields.put(enclosedFieldCounter++, new EnclosedField(numberLetterMap.get(i), (j + 1)));
                            enclosedFields.put(enclosedFieldCounter++, new EnclosedField(numberLetterMap.get(i - 1), (j + 1)));
                            enclosedFields.put(enclosedFieldCounter++, new EnclosedField(numberLetterMap.get(i + 1), (j + 1)));

//                            field[i][j + 1] = ".";
//                            field[i - 1][j + 1] = ".";
//                            field[i + 1][j + 1] = ".";
                        }
                        coordinates.put(coordinatesCounter++, new CoordinateUnit(numberLetterMap.get(i), j));
                        enclosedFields.put(enclosedFieldCounter++, new EnclosedField(numberLetterMap.get(i + 1), (j)));
                        enclosedFields.put(enclosedFieldCounter++, new EnclosedField(numberLetterMap.get(i - 1), (j)));

                        field[i][j] = String.valueOf('O');
//                        field[i + 1][j] = ".";
//                        field[i - 1][j] = ".";
                        ship.setSize(ship.getSize() - 1);

                        //the ship is touching borders
                        //placed on A field
                    } else if (ship.getRearLetter() == 1 && i == ship.getRearLetter()) {
                        if (j >= ship.getRearNumber() && j <= ship.getForeNumber()) {

                            coordinates.put(coordinatesCounter++, new CoordinateUnit(numberLetterMap.get(i), j));
                            enclosedFields.put(enclosedFieldCounter++, new EnclosedField(numberLetterMap.get(i + 1), (j)));

                            field[i][j] = String.valueOf('O');
//                            field[i + 1][j] = ".";
                            ship.setSize(ship.getSize() - 1);

                            if (j == ship.getRearNumber() && ship.getRearNumber() != 1) {
                                enclosedFields.put(enclosedFieldCounter++, new EnclosedField(numberLetterMap.get(i), (j - 1)));
                                enclosedFields.put(enclosedFieldCounter++, new EnclosedField(numberLetterMap.get(i + 1), (j - 1)));

//                                field[i][j - 1] = ".";
//                                field[i + 1][j - 1] = ".";
                            }
                            if (j == ship.getForeNumber() && ship.getForeNumber() != 10) {
                                enclosedFields.put(enclosedFieldCounter++, new EnclosedField(numberLetterMap.get(i), (j + 1)));
                                enclosedFields.put(enclosedFieldCounter++, new EnclosedField(numberLetterMap.get(i + 1), (j + 1)));

//                                field[i][j + 1] = ".";
//                                field[i + 1][j + 1] = ".";
                            }
                        }
                        //placed on J field
                    } else if (ship.getRearLetter() == 10 && i == ship.getRearLetter()) {
                        if (j >= ship.getRearNumber() && j <= ship.getForeNumber()) {
                            coordinates.put(coordinatesCounter++, new CoordinateUnit(numberLetterMap.get(i), j));
                            enclosedFields.put(enclosedFieldCounter++, new EnclosedField(numberLetterMap.get(i - 1), (j)));

                            field[i][j] = String.valueOf('O');
//                            field[i - 1][j] = ".";
                            ship.setSize(ship.getSize() - 1);
                        }
                        if (j == ship.getRearNumber() && ship.getRearNumber() != 1) {
                            enclosedFields.put(enclosedFieldCounter++, new EnclosedField(numberLetterMap.get(i), (j - 1)));
                            enclosedFields.put(enclosedFieldCounter++, new EnclosedField(numberLetterMap.get(i - 1), (j - 1)));

//                            field[i][j - 1] = ".";
//                            field[i - 1][j - 1] = ".";
                        }
                        if (j == ship.getForeNumber() && ship.getForeNumber() != 10) {
                            enclosedFields.put(enclosedFieldCounter++, new EnclosedField(numberLetterMap.get(i), (j + 1)));
                            enclosedFields.put(enclosedFieldCounter++, new EnclosedField(numberLetterMap.get(i - 1), (j + 1)));

//                            field[i][j + 1] = ".";
//                            field[i - 1][j + 1] = ".";
                        }
                    }
                }
            }
            //For vertically placed ships [meaning Letters are not equal]
        } else {
            for (int i = 1; i < field.length && continueProcessing; i++) {
                for (int j = 1; j < field[i].length; j++) {
                    if (j != ship.getRearNumber() || j != ship.getForeNumber()) {
                        continue;
                    }
                    if (ship.getSize() == 0) {
                        ship.setAfloat(false);
                        continueProcessing = false;
                    }
                    if (i >= 1 && j > 1 && i < field.length - 1 && j < field.length - 1) {
                        if ((j == ship.getRearNumber() && j == ship.getForeNumber()) && (i >= ship.getRearLetter() && i <= ship.getForeLetter())) {
                            if (i == ship.getRearLetter() && ship.getRearLetter() != 1) {
                                enclosedFields.put(enclosedFieldCounter++, new EnclosedField(numberLetterMap.get(i - 1), (j)));
                                enclosedFields.put(enclosedFieldCounter++, new EnclosedField(numberLetterMap.get(i - 1), (j - 1)));
                                enclosedFields.put(enclosedFieldCounter++, new EnclosedField(numberLetterMap.get(i - 1), (j + 1)));

//                                field[i - 1][j] = ".";
//                                field[i - 1][j - 1] = ".";
//                                field[i - 1][j + 1] = ".";
                            }
                            if (i == ship.getForeLetter()) {
                                enclosedFields.put(enclosedFieldCounter++, new EnclosedField(numberLetterMap.get(i + 1), (j)));
                                enclosedFields.put(enclosedFieldCounter++, new EnclosedField(numberLetterMap.get(i + 1), (j - 1)));
                                enclosedFields.put(enclosedFieldCounter++, new EnclosedField(numberLetterMap.get(i + 1), (j + 1)));

//                                field[i + 1][j] = ".";
//                                field[i + 1][j - 1] = ".";
//                                field[i + 1][j + 1] = ".";
                            }
                            coordinates.put(coordinatesCounter++, new CoordinateUnit(numberLetterMap.get(i), j));
                            enclosedFields.put(enclosedFieldCounter++, new EnclosedField(numberLetterMap.get(i), (j + 1)));
                            enclosedFields.put(enclosedFieldCounter++, new EnclosedField(numberLetterMap.get(i), (j - 1)));

                            field[i][j] = String.valueOf('O');
//                            field[i][j + 1] = ".";
//                            field[i][j - 1] = ".";
                            ship.setSize(ship.getSize() - 1);
                        }
                        if (i > ship.getForeLetter()) {
                            break;
                        }
                    }
                    //the ship is touching borders
                    //placed on 1 column
                    //TODO finish vertical case!
                    else if (ship.getRearNumber() == 1 && ship.getForeNumber() == 1 && j == ship.getRearNumber()) {
                        if (i >= ship.getRearLetter() && i <= ship.getForeLetter()) {
                            if (ship.getRearLetter() == 1 && i == ship.getForeLetter()) {
                                enclosedFields.put(enclosedFieldCounter++, new EnclosedField(numberLetterMap.get(i + 1), (j)));
                                enclosedFields.put(enclosedFieldCounter++, new EnclosedField(numberLetterMap.get(i + 1), (j + 1)));

//                                field[i + 1][j] = ".";
//                                field[i + 1][j + 1] = ".";
                            }
                            if (ship.getForeLetter() == 10 && i == ship.getRearLetter()) {
                                enclosedFields.put(enclosedFieldCounter++, new EnclosedField(numberLetterMap.get(i - 1), (j)));
                                enclosedFields.put(enclosedFieldCounter++, new EnclosedField(numberLetterMap.get(i - 1), (j + 1)));

//                                field[i - 1][j] = ".";
//                                field[i - 1][j + 1] = ".";
                            }
                            if (ship.getRearLetter() != 1 && ship.getForeLetter() != 10 && i == ship.getRearLetter()) {
                                enclosedFields.put(enclosedFieldCounter++, new EnclosedField(numberLetterMap.get(i - 1), (j)));
                                enclosedFields.put(enclosedFieldCounter++, new EnclosedField(numberLetterMap.get(i - 1), (j + 1)));

//                                field[i - 1][j] = ".";
//                                field[i - 1][j + 1] = ".";
                            } else if (ship.getRearLetter() != 1 && ship.getForeLetter() != 10 && i == ship.getForeLetter()) {
                                enclosedFields.put(enclosedFieldCounter++, new EnclosedField(numberLetterMap.get(i + 1), (j)));
                                enclosedFields.put(enclosedFieldCounter++, new EnclosedField(numberLetterMap.get(i + 1), (j + 1)));

//                                field[i + 1][j] = ".";
//                                field[i + 1][j + 1] = ".";
                            }
                            coordinates.put(coordinatesCounter++, new CoordinateUnit(numberLetterMap.get(i), j));
                            enclosedFields.put(enclosedFieldCounter++, new EnclosedField(numberLetterMap.get(i), (j + 1)));

                            field[i][j] = String.valueOf('O');
//                            field[i][j + 1] = ".";
                            ship.setSize(ship.getSize() - 1);
                        }

                        //placed on 10 column
                    } else if (ship.getRearNumber() == 10 && j == ship.getRearNumber()) {
                        if (i >= ship.getRearLetter() && i <= ship.getForeLetter()) {
                            if (ship.getRearLetter() == 1 && i == ship.getForeLetter()) {
                                enclosedFields.put(enclosedFieldCounter++, new EnclosedField(numberLetterMap.get(i + 1), (j)));
                                enclosedFields.put(enclosedFieldCounter++, new EnclosedField(numberLetterMap.get(i + 1), (j - 1)));

//                                field[i + 1][j] = ".";
//                                field[i + 1][j - 1] = ".";
                            }
                            if (ship.getForeLetter() == 10 && i == ship.getRearLetter()) {
                                enclosedFields.put(enclosedFieldCounter++, new EnclosedField(numberLetterMap.get(i - 1), (j)));
                                enclosedFields.put(enclosedFieldCounter++, new EnclosedField(numberLetterMap.get(i - 1), (j - 1)));

//                                field[i - 1][j] = ".";
//                                field[i - 1][j - 1] = ".";
                            }
                            if (ship.getRearLetter() != 1 && ship.getForeLetter() != 10 && i == ship.getRearLetter()) {
                                enclosedFields.put(enclosedFieldCounter++, new EnclosedField(numberLetterMap.get(i - 1), (j)));
                                enclosedFields.put(enclosedFieldCounter++, new EnclosedField(numberLetterMap.get(i - 1), (j - 1)));

//                                field[i - 1][j] = ".";
//                                field[i - 1][j - 1] = ".";
                            } else if (ship.getRearLetter() != 1 && ship.getForeLetter() != 10 && i == ship.getForeLetter()) {
                                enclosedFields.put(enclosedFieldCounter++, new EnclosedField(numberLetterMap.get(i + 1), (j)));
                                enclosedFields.put(enclosedFieldCounter++, new EnclosedField(numberLetterMap.get(i + 1), (j - 1)));

//                                field[i + 1][j] = ".";
//                                field[i + 1][j - 1] = ".";
                            }
                            coordinates.put(coordinatesCounter++, new CoordinateUnit(numberLetterMap.get(i), j));
                            enclosedFields.put(enclosedFieldCounter++, new EnclosedField(numberLetterMap.get(i), (j - 1)));

                            field[i][j] = String.valueOf('O');
//                            field[i][j - 1] = ".";
                            ship.setSize(ship.getSize() - 1);
                        }
                    }
                }
            }
            Field.getInstance().setBattleField(field);
        }
        ship.setEnclosedFields(enclosedFields);
        ship.setCoordinates(coordinates);
        return ship;
    }

    private boolean validateShipPosition(Ship ship) throws IncorrectShipSizeException, WrongShipLocationException, ShipCoordinatesOutTheBoardException {

        if (ship.getRearLetter() >= LOWER_BOUNDARY &&
                ship.getRearNumber() >= LOWER_BOUNDARY &&
                ship.getForeLetter() >= LOWER_BOUNDARY &&
                ship.getForeNumber() >= LOWER_BOUNDARY &&
                ship.getRearLetter() <= UPPER_BOUNDARY &&
                ship.getRearNumber() <= UPPER_BOUNDARY &&
                ship.getForeLetter() <= UPPER_BOUNDARY &&
                ship.getForeNumber() <= UPPER_BOUNDARY) {
        } else {
            throw new ShipCoordinatesOutTheBoardException("One of the boundaries is out of range!");
        }
        //Checking correct length of the ship
        int userIndicatedSize;

        if (ship.getRearLetter() == ship.getForeLetter()) {
            userIndicatedSize = ship.getForeNumber() - ship.getRearNumber()+1;
            if (userIndicatedSize != ship.getProductionSize()) {
                throw new IncorrectShipSizeException("Error! Wrong length of the" + ship.getName() + "! Try again:");
            }
        }

        //Checking for obliquely coordinates
        if (ship.getRearLetter() != ship.getForeLetter() && ship.getRearNumber() != ship.getForeNumber()) {
            throw new WrongShipLocationException("Error! Wrong ship location! Try again:");
        }
        return true;
    }
}
