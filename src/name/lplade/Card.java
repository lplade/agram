package name.lplade;

import java.util.HashMap;
import java.util.function.IntBinaryOperator;

/**
 * Created by lade on 10/18/16.
 */


class Card {

    //Cards needed are A, 10, 9, 8, 7, 6, 5, 4, 3
    //Ace is high - give numeric value of 11.

    //Ways we might want to use data about cards
    //- strings we can build into descriptions, e.g. "ace of spades"
    //- short symbols for when we're displaying a bunch of cards "9♣"
    //- numeric values to test if aces are better than twos

    private int value; // 1 means ace here
    private int suit; //0xA0, 0xB0, 0xC0, 0xD0 - see SuitManager()
    private boolean faceDown; //for display purposes

    Card(int suit, int value) {
        this.suit = suit;
        this.value = value;
        this.faceDown = false;
    }

    public String getCardString(){
        //return something like "9♣"
        //TODO make hearts and diamonds red?
        String cardString = NumberManager.getNumberStringShort(this.value) + SuitManager.getSuitGlyph(this.suit);
        return cardString;
    }

    //TODO getCardGlyph to return single Unicode glyph representing the card.

    //returns number that appears on face of card, 2 through A;
    public String getCardNumberStr(){
        String number = NumberManager.getNumberString(this.value);
        return getCardString();
    }

    //returns numeric value for in-game comparisons
    public int getCardValue(){
        //TODO assert return value from 2 to 11;
        int value = NumberManager.getNumberValue(this.value);
        return value;
    }

    //return suit as a word "diamonds" etc.
    public String getCardSuit(){
        String suit = SuitManager.getSuitString(this.suit);
        return suit;
    }


    //inner class to encode suits
    //based on the FurnaceTypeManager from HVAC program
    private static class SuitManager {
        // These values key to values in Unicode playing code standard
        // https://en.wikipedia.org/wiki/Playing_cards_in_Unicode
        // In theory, we can add them to a card value and get a Unicode glyph.
        static final int SPADES = 0xA0;
        static final int HEARTS = 0xB0;
        static final int DIAMONDS = 0xC0;
        static final int CLUBS = 0xD0;

        //Suit symbols
        //mind the plurals on these
        static final char SPADE = 0x2660;
        static final char HEART = 0x2665;
        static final char DIAMOND = 0x2666;
        static final char CLUB = 0x2663;

        static HashMap<Integer, String> suits;
        static HashMap<Integer, String> suitGlyphs;

        //Static initialization block
        static {
            suits = new HashMap<Integer, String>();
            suits.put(SPADES,"spades");
            suits.put(HEARTS,"hearts");
            suits.put(DIAMONDS,"diamonds");
            suits.put(CLUBS,"clubs");
        }

        static {
            suitGlyphs = new HashMap<Integer, String>();
            suitGlyphs.put(SPADES, Character.toString(SPADE));
            suitGlyphs.put(HEARTS, Character.toString(HEART));
            suitGlyphs.put(DIAMONDS, Character.toString(DIAMOND));
            suitGlyphs.put(CLUBS, Character.toString(CLUB));
        }

        static String getSuitString(int suitInt) {
            if (suits.containsKey(suitInt)) {
                return suits.get(suitInt);
            } else {
                return "ERR NOT A SUIT!"; //TODO probably a better way to handle this condition. Assert?
            }
        }

        static String getSuitGlyph(int suitInt) {
            if (suitGlyphs.containsKey(suitInt)) {
                return suitGlyphs.get(suitInt);
            } else {
                return "X"; //TODO better handling. Assert?
            }
        }

    }

    private static class NumberManager {
        //Internally store card values as integers 1-10
        //These match Unicode mapper for future output handling
        //provide method to getting in-game comparison value, mapping Ace to 11
        //and method to get string, mapping Ace to 'A'

        //MAYBE overkill for remapping the value of a single card, but should let us have cleaner code by
        // avoiding special conditions for ace every time we need to use it

        static final int ACE = 0x01;
        static HashMap<Integer, String> numberStrings;
        static HashMap<Integer, String> numberStringsShort;
        static HashMap<Integer, Integer> numberValues;

        static {
            numberStrings = new HashMap<Integer, String>();
            numberStrings.put(ACE,"ace");
            //the remaining cards are all numbers
            for(val=2; val<=10; val++) {
                numberStrings.put(val, Integer.toString(val));
            }
        }

        //similar, but ace is 'A'
        static {
            numberStringsShort = new HashMap<Integer, String>();
            numberStringsShort.put(ACE,"A");
            //the remaining cards are all numbers
            for(val=2; val<=10; val++) {
                numberStringsShort.put(val, Integer.toString(val));
            }
        }

        static {
            numberValues = new HashMap<Integer, Integer>();
            numberValues.put(ACE, 11);
            for(val=2; val<=10; val++) {
                numberValues.put(val, val);
            }
        }

        static String getNumberString(int numberInt) {
            if (numberStrings.containsKey(numberInt)) {
                return numberStrings.get(numberInt);
            } else {
                return "!!"; //TODO probably a better way to handle this condition. Assert?
            }
        }

        static Integer getNumberValue(int numberInt) {
            if (numberValues.containsKey(numberInt)) {
                return numberValues.get(numberInt);
            } else {
                return 0; //TODO probably a better way to handle this condition. Assert?
            }
        }


    }

}
