package name.lplade;

import java.util.HashMap;

class NumberManager {
    //Internally store card values as integers 1-10
    //These match Unicode mapper for future output handling
    //provide method to getting in-game comparison value, mapping Ace to 11
    //and method to get string, mapping Ace to 'A'

    //MAYBE overkill for remapping the value of a single card, but should let us have cleaner code by
    // avoiding special conditions for ace every time we need to use it

    //This gets used by other classes and needed to move out into its own class

    static final int ACE = 0x01;
    private static HashMap<Integer, String> numberStrings;
    private static HashMap<Integer, String> numberStringsShort;
    private static HashMap<Integer, Integer> numberValues;

    //We map "2" so we can loop over it safely, but we don't put it in a deck

    //numberStrings: "ace","3","4"..."10"
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

    //numberValues: 11, 3, 4 ... 10
    static {
        numberValues = new HashMap<Integer, Integer>();
        numberValues.put(ACE, 11);
        for(int val=2; val<=10; val++) {
            numberValues.put(val, val);
        }
    }

    static String getNumberString(int numberInt) {
        assert numberStrings.containsKey(numberInt) : numberInt;

        if (numberStrings.containsKey(numberInt)) {
            return numberStrings.get(numberInt);
        } else {
            throw new AssertionError(numberInt);
        }
    }

    static String getNumberStringShort(int numberInt) {
        assert numberStringsShort.containsKey(numberInt) : numberInt;

        if (numberStringsShort.containsKey(numberInt)) {
            return numberStringsShort.get(numberInt);
        } else {
            throw new AssertionError(numberInt);
        }
    }

    static Integer getNumberValue(int numberInt) {
        assert numberValues.containsKey(numberInt) : numberInt;

        if (numberValues.containsKey(numberInt)) {
            return numberValues.get(numberInt);
        } else {
            throw new AssertionError(numberInt);
        }
    }
}