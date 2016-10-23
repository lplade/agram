package name.lplade;

//import java.util.HashMap;

/**
 * Created by lade on 10/20/16.
 */
//class to encode suits
//based on the FurnaceTypeManager from HVAC program

// move from subclass of Card into own class
class SuitManager {

    //Wow, enum!
    enum Suit {
        SPADES, HEARTS, DIAMONDS, CLUBS
    }

    // These values key to values in Unicode playing code standard
    // https://en.wikipedia.org/wiki/Playing_cards_in_Unicode
    // In theory, we can add them to a card value and get a Unicode glyph.
    static int getUnicodeMask(Suit s){
        switch(s) {
            case SPADES:
                return 0xA0;
            case HEARTS:
                return 0xB0;
            case DIAMONDS:
                return 0xC0;
            case CLUBS:
                return 0xD0;
            default:
                throw new AssertionError(s);
        }
    }

    static char getSuitGlyph (Suit s) {
        switch(s){
            case SPADES:
                return 0x2660;
            case HEARTS:
                return 0x2665;
            case DIAMONDS:
                return 0x2666;
            case CLUBS:
                return 0x2663;
            default:
                throw new AssertionError(s);
        }
    }

    static String getSuitString(Suit s) {
        switch(s){
            case SPADES:
                return "spades";
            case HEARTS:
                return "hearts";
            case DIAMONDS:
                return "diamonds";
            case CLUBS:
                return "clubs";
            default:
                throw new AssertionError(s);
        }
    }

    //old pre-enum code follows



/*    private static final int SPADES = 0xA0;
    private static final int HEARTS = 0xB0;
    private static final int DIAMONDS = 0xC0;
    private static final int CLUBS = 0xD0;*/



/*    //Suit symbols
    //mind the plurals on these
    private static final char SPADE = 0x2660;
    private static final char HEART = 0x2665;
    private static final char DIAMOND = 0x2666;
    private static final char CLUB = 0x2663;*/

/*    private static HashMap<Integer, String> suits;
    private static HashMap<Integer, String> suitGlyphs;

    //Static initialization blocks
    //Map internal values for suits to words
    static {
        suits = new HashMap<Integer, String>();
        suits.put(SPADES,"spades");
        suits.put(HEARTS,"hearts");
        suits.put(DIAMONDS,"diamonds");
        suits.put(CLUBS,"clubs");
    }*/



/*    //Map internal values for suits to glyph strings
    static {
        suitGlyphs = new HashMap<Integer, String>();
        suitGlyphs.put(SPADES, Character.toString(SPADE));
        suitGlyphs.put(HEARTS, Character.toString(HEART));
        suitGlyphs.put(DIAMONDS, Character.toString(DIAMOND));
        suitGlyphs.put(CLUBS, Character.toString(CLUB));
    }*/

/*    static String getSuitString(int suitInt) {
        if (suits.containsKey(suitInt)) {
            return suits.get(suitInt);
        } else {
            return "ERR NOT A SUIT!"; //TODO probably a better way to handle this condition. Assert?
        }
    }*/

/*    static String getSuitGlyph(int suitInt) {
        if (suitGlyphs.containsKey(suitInt)) {
            return suitGlyphs.get(suitInt);
        } else {
            return "X"; //TODO better handling. Assert?
        }
    }*/

/*    // http://stackoverflow.com/questions/20278639/hashmap-reverse-key-lookup
    static int getSuitCodeByStr(String suitString) {
        for (HashMap.Entry<Integer, String> suitEntry : suits.entrySet()) {
            if (suitEntry.getValue().equals(suitString)) {
                return suitEntry.getKey();
            }
        }
       throw new AssertionError(suitString); //we should never get here
    }*/
}
