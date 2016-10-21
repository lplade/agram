package name.lplade;

import java.util.HashMap;

/**
 * Created by lade on 10/20/16.
 */
class NumberManager {
    //Internally store card values as integers 1-10
    //These match Unicode mapper for future output handling
    //provide method to getting in-game comparison value, mapping Ace to 11
    //and method to get string, mapping Ace to 'A'

    //MAYBE overkill for remapping the value of a single card, but should let us have cleaner code by
    // avoiding special conditions for ace every time we need to use it

    //This gets used by other classes and needed to move out into its own class

    static final int ACE = 0x01;
    static HashMap<Integer, String> numberStrings;
    static HashMap<Integer, String> numberStringsShort;
    static HashMap<Integer, Integer> numberValues;

    static {
        numberStrings = new HashMap<Integer, String>();
        numberStrings.put(ACE,"ace");
        //the remaining cards are all numbers
        for(int val=2; val<=10; val++) {
            numberStrings.put(val, Integer.toString(val));
        }
    }

    //similar, but ace is 'A'
    static {
        numberStringsShort = new HashMap<Integer, String>();
        numberStringsShort.put(ACE,"A");
        //the remaining cards are all numbers
        for(int val=2; val<=10; val++) {
            numberStringsShort.put(val, Integer.toString(val));
        }
    }

    static {
        numberValues = new HashMap<Integer, Integer>();
        numberValues.put(ACE, 11);
        for(int val=2; val<=10; val++) {
            numberValues.put(val, val);
        }
    }

    static String getNumberString(int numberInt) {
        assert numberStrings.containsKey(numberInt);

        if (numberStrings.containsKey(numberInt)) {
            return numberStrings.get(numberInt);
        } else {
            return "!!"; //TODO probably a better way to handle this condition. Assert?
        }
    }

    static String getNumberStringShort(int numberInt) {
        assert numberStrings.containsKey(numberInt);

        if (numberStrings.containsKey(numberInt)) {
            return numberStrings.get(numberInt);
        } else {
            return "!"; //TODO assert?
        }
    }

    static Integer getNumberValue(int numberInt) {
        assert numberValues.containsKey(numberInt);

        if (numberValues.containsKey(numberInt)) {
            return numberValues.get(numberInt);
        } else {
            return 0; //TODO probably a better way to handle this condition. Assert?
        }
    }
}