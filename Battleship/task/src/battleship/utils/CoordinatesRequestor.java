package battleship.utils;

import battleship.exceptions.ShotOutTheBoardException;
import battleship.vessels.Ship;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static battleship.utils.NavigationUtils.letterNumberMap;

public class CoordinatesRequestor {

    private final static Scanner scanner = new Scanner(System.in);

    public static List<Integer> requestCoordinates(Ship ship, boolean repeat) {
        if (repeat) {
            System.out.print("> ");
        } else {
            System.out.printf("\nEnter the coordinates of the %s (%d cells) \n", ship.getName(), ship.getProductionSize());
            System.out.print("\n> ");
        }

        String userInput = scanner.nextLine();

        System.out.print("\n");
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

    public static String requestUserInput() {
        System.out.print("\n>");
        String result = null;
        result = scanner.nextLine();
        System.out.print("\n");

        try {
            List<Character> letters = findLetters(result);
            List<Integer> numbers = findNumbers(result);

            if ((letters.get(0) > 'J' && letters.get(0) < 'a') || (numbers.get(0) > 10 || numbers.get(0) < 1)) {
                throw new ShotOutTheBoardException("\nError! You entered wrong coordinates! Try again:");
            }
        } catch (ShotOutTheBoardException e) {
            System.out.println(e.getMessage());
            result = requestUserInput();
        }
        return result;
    }

    private static List<Character> findLetters(String stringToSearch) {
        Pattern charPattern = Pattern.compile("\\p{L}");
        Matcher matcher = charPattern.matcher(stringToSearch);

        List<Character> charList = new ArrayList<>();
        while (matcher.find()) {
            String upperCaseLetter = matcher.group().toUpperCase(Locale.ROOT);
            charList.add(upperCaseLetter.charAt(0));
        }
        return charList;
    }

    public static List<Integer> findNumbers(String stringToSearch) {
        Pattern integerPattern = Pattern.compile("\\d+");
        Matcher matcher = integerPattern.matcher(stringToSearch);

        List<Integer> integerList = new ArrayList<>();
        while (matcher.find()) {
            integerList.add(Integer.parseInt(matcher.group()));
        }
        return integerList;
    }

    public static void passMove() {
        System.out.println("\nPress Enter and pass the move to another player\n...");
        while (true) {
            try {
                if ('\n' == (char) System.in.read()) break;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
