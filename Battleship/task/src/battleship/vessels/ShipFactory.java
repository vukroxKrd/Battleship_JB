package battleship.vessels;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static battleship.utils.NavigationUtils.letterNumberMap;

public class ShipFactory {
    Scanner scanner = new Scanner(System.in);

    public Ship createShip(VesselType type, List<Integer> coordinates) {
        Ship ship = null;

        int rearLetter = coordinates.get(0);
        int rearNumber = coordinates.get(1);
        int foreLetter = coordinates.get(2);
        int foreNumber = coordinates.get(3);


        switch (type) {
            case AIRCRAFT_CARRIER:
                ship = new AircraftCarrier(rearLetter, rearNumber, foreLetter, foreNumber);
                break;
            case BATTLESHIP:
                ship = new Battleship(rearLetter, rearNumber, foreLetter, foreNumber);
                break;
            case SUBMARINE:
                ship = new Submarine(rearLetter, rearNumber, foreLetter, foreNumber);
                break;
            case CRUISER:
                ship = new Cruiser(rearLetter, rearNumber, foreLetter, foreNumber);
                break;
            case DESTROYER:
                ship = new Destroyer(rearLetter, rearNumber, foreLetter, foreNumber);
                break;
        }
        return ship;
    }

    public List<Integer> requestCoordinates(Ship ship, boolean repeat) {
        if (repeat) {
            System.out.print("> ");
        } else {
            System.out.printf("Enter the coordinates of the %s (%d cells) \n", ship.getName(), ship.getProductionSize());
            System.out.print("\n> ");
        }

        String userInput = scanner.nextLine();

        List<Integer> numbers = findNumbers(userInput);
        List<Character> letters = findLetters(userInput);
        List<Integer> result = new ArrayList<>();
        int rearLetter = letterNumberMap.get(letters.get(0));
        int rearNumber = numbers.get(0);
        int foreLetter = letterNumberMap.get(letters.get(1));
        int foreNumber = numbers.get(1);

        //handling 2 edge cases here when user breaks the pattern of the input of game fields
        if (rearLetter == foreLetter && rearNumber > foreNumber) {
            int temp = rearNumber;
            rearNumber = foreNumber;
            foreNumber = temp;
        }
        if (rearNumber == foreNumber && rearLetter > foreLetter) {
            int temp = rearLetter;
            rearLetter = foreLetter;
            foreLetter = temp;
        }

        result.add(rearLetter);
        result.add(rearNumber);
        result.add(foreLetter);
        result.add(foreNumber);
        return result;
    }

    private List<Character> findLetters(String stringToSearch) {
        Pattern charPattern = Pattern.compile("\\p{L}");
        Matcher matcher = charPattern.matcher(stringToSearch);

        List<Character> charList = new ArrayList<>();
        while (matcher.find()) {
            String upperCaseLetter = matcher.group().toUpperCase(Locale.ROOT);
            charList.add(upperCaseLetter.charAt(0));
        }
        return charList;
    }

    //
    private List<Integer> findNumbers(String stringToSearch) {
        Pattern integerPattern = Pattern.compile("\\d+");
        Matcher matcher = integerPattern.matcher(stringToSearch);

        List<Integer> integerList = new ArrayList<>();
        while (matcher.find()) {
            integerList.add(Integer.parseInt(matcher.group()));
        }
        return integerList;
    }
}