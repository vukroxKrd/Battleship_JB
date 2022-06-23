package battleship;

import battleship.vessels.Ship;

import java.util.HashMap;
import java.util.Map;

public class Field {
    private String[][] battleField;
    private static Field instance;
    public static final int LOWER_BOUNDARY = 1;
    public static final int UPPER_BOUNDARY = 11;

    private static Map<Character, Integer> letterNumberMap = new HashMap<>();

    static {
        letterNumberMap.put('A', 1);
        letterNumberMap.put('B', 2);
        letterNumberMap.put('C', 3);
        letterNumberMap.put('D', 4);
        letterNumberMap.put('E', 5);
        letterNumberMap.put('F', 6);
        letterNumberMap.put('G', 7);
        letterNumberMap.put('H', 8);
        letterNumberMap.put('I', 9);
        letterNumberMap.put('J', 10);
    }

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

    public boolean placeShipOnMap(Ship ship) {
        String[] rear = ship.getRear();
        String[] fore = ship.getFore();
        String[][] field = Field.getInstance().getBattleField();

        int rearLetter = letterNumberMap.get(rear[0].charAt(0));
        int rearNumber = Integer.parseInt(rear[1]);
        int foreLetter = letterNumberMap.get(fore[0].charAt(0));
        int foreNumber = Integer.parseInt(fore[1]);

        int shipSize;
        if (foreNumber - rearNumber != 0) {
            shipSize = (foreNumber + 1) - rearNumber;
        } else {
            shipSize = (foreLetter + 1) - rearLetter;
        }

        boolean continueProcessing = true;

        if (rearLetter >= LOWER_BOUNDARY &&
                rearNumber >= LOWER_BOUNDARY &&
                foreLetter >= LOWER_BOUNDARY &&
                foreNumber >= LOWER_BOUNDARY &&
                rearLetter <= UPPER_BOUNDARY &&
                rearNumber <= UPPER_BOUNDARY &&
                foreLetter <= UPPER_BOUNDARY &&
                foreNumber <= UPPER_BOUNDARY) {
        } else {
            throw new IndexOutOfBoundsException("One of the boundaries is out of range!");
        }

        //For horizontal ships they have the same letter in the coordinates
        if (rearLetter == foreLetter) {
            for (int i = 1; i < field.length && continueProcessing; i++) {
                for (int j = 1; j < field[i].length; j++) {
                    if (shipSize == 0) {
                        continueProcessing = false;
                    }
                    // the ship is not touching borders
                    if (i > 1 && j > 1 && i < field.length - 1 && j < field.length - 1) {
                        if (i >= rearLetter && j >= rearNumber && i <= foreLetter && j <= foreNumber) {
                            field[i][j] = String.valueOf('O');
                            field[i + 1][j] = ".";
                            field[i - 1][j] = ".";
                            shipSize = shipSize - 1;
                            if (i == rearLetter && j == rearNumber) {
                                field[i][j - 1] = ".";
                                field[i - 1][j - 1] = ".";
                                field[i + 1][j - 1] = ".";
                            }
                            if (i == foreLetter && j == foreNumber) {
                                field[i][j + 1] = ".";
                                field[i - 1][j + 1] = ".";
                                field[i + 1][j + 1] = ".";
                            }
                        }
                        //the ship is touching borders
                        //placed on A field
                    } else if (rearLetter == 1 && i == rearLetter) {
                        if (j >= rearNumber && j <= foreNumber) {
                            field[i][j] = String.valueOf('O');
                            field[i + 1][j] = ".";
                            shipSize = shipSize - 1;
                            if (j == rearNumber && rearNumber != 1) {
                                field[i][j - 1] = ".";
                                field[i + 1][j - 1] = ".";
                            }
                            if (j == foreNumber && foreNumber != 10) {
                                field[i][j + 1] = ".";
                                field[i + 1][j + 1] = ".";
                            } else {
                                field[i + 1][j] = ".";
                            }
                        }
                        //placed on J field
                    } else if (rearLetter == 10 && i == rearLetter) {
                        if (j >= rearNumber && j <= foreNumber) {
                            field[i][j] = String.valueOf('O');
                            field[i - 1][j] = ".";
                            shipSize = shipSize - 1;

                            if (j == rearNumber && rearNumber != 1) {
                                field[i][j - 1] = ".";
                                field[i - 1][j - 1] = ".";
                            } else {
                                field[i - 1][j] = ".";
                            }
                            if (j == foreNumber && foreNumber != 10) {
                                field[i][j + 1] = ".";
                                field[i - 1][j + 1] = ".";
                            } else {
                                field[i - 1][j] = ".";
                            }
                        }
                    }
                }
            }
            //For vertically placed ships [meaning Letters are not equal]
        } else {
            for (int i = 1; i < field.length && continueProcessing; i++) {
                for (int j = 1; j < field[i].length; j++) {
                    if (j != rearNumber || j != foreNumber) {
                        continue;
                    }
                    if (shipSize == 0) {
                        continueProcessing = false;
                    }
                    if (i > 1 && j > 1 && i < field.length - 1 && j < field.length - 1) {
                        if ((j == rearNumber && j == foreNumber) && (i >= rearLetter && i <= foreLetter)) {
                            if (i == rearLetter) {
                                field[i - 1][j] = ".";
                                field[i - 1][j - 1] = ".";
                                field[i - 1][j + 1] = ".";
                            }
                            if (i == foreLetter) {
                                field[i + 1][j] = ".";
                                field[i + 1][j - 1] = ".";
                                field[i + 1][j + 1] = ".";
                            }
                            field[i][j] = String.valueOf('O');
                            field[i][j + 1] = ".";
                            field[i][j - 1] = ".";
                            shipSize = shipSize - 1;
                        }
                        if (i > foreLetter) {
                            break;
                        }
                    }
                    //the ship is touching borders
                    //placed on 1 column
                    //TODO finish vertical case!
                    else if (rearNumber == 1 && foreNumber == 1 && j == rearNumber) {
                        if (i >= rearLetter && i <= foreLetter) {
                            if (rearLetter == 1 && i == foreLetter) {
                                field[i + 1][j] = ".";
                                field[i + 1][j + 1] = ".";
                            }
                            if (foreLetter == 10 && i == rearLetter) {
                                field[i - 1][j] = ".";
                                field[i - 1][j + 1] = ".";
                            }
                            if (rearLetter != 1 && foreLetter != 10 && i == rearLetter) {
                                field[i - 1][j] = ".";
                                field[i - 1][j + 1] = ".";
                            } else if (rearLetter != 1 && foreLetter != 10 && i == foreLetter) {
                                field[i + 1][j] = ".";
                                field[i + 1][j + 1] = ".";
                            }
                            field[i][j] = String.valueOf('O');
                            field[i][j + 1] = ".";
                            shipSize = shipSize - 1;
                        }

                        //placed on 10 column
                    } else if (rearNumber == 10 && j == rearNumber) {
                        if (i >= rearLetter && i <= foreLetter) {
                            if (rearLetter == 1 && i == foreLetter) {
                                field[i + 1][j] = ".";
                                field[i + 1][j - 1] = ".";
                            }
                            if (foreLetter == 10 && i == rearLetter) {
                                field[i - 1][j] = ".";
                                field[i - 1][j - 1] = ".";
                            }
                            if (rearLetter != 1 && foreLetter != 10 && i == rearLetter) {
                                field[i - 1][j] = ".";
                                field[i - 1][j - 1] = ".";
                            } else if (rearLetter != 1 && foreLetter != 10 && i == foreLetter) {
                                field[i + 1][j] = ".";
                                field[i + 1][j - 1] = ".";
                            }
                            field[i][j] = String.valueOf('O');
                            field[i][j - 1] = ".";
                            shipSize = shipSize - 1;
                        }
                    }
                }
            }
            Field.getInstance().setBattleField(field);
        }
        return true;
    }
}
