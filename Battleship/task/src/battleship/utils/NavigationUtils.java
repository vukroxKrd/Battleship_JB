package battleship.utils;

import java.util.HashMap;
import java.util.Map;

public class NavigationUtils {

    public static Map<Character, Integer> letterNumberMap = new HashMap<>();
    public static Map<Integer, Character> numberLetterMap = new HashMap<>();

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

    static {
        numberLetterMap.put(1, 'A');
        numberLetterMap.put(2, 'B');
        numberLetterMap.put(3, 'C');
        numberLetterMap.put(4, 'D');
        numberLetterMap.put(5, 'E');
        numberLetterMap.put(6, 'F');
        numberLetterMap.put(7, 'G');
        numberLetterMap.put(8, 'H');
        numberLetterMap.put(9, 'I');
        numberLetterMap.put(10, 'J');
    }
}
