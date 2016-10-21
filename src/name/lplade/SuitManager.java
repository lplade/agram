package name.lplade;

import java.util.HashMap;

/**
 * Created by lade on 10/20/16.
 */
//class to encode suits
//based on the FurnaceTypeManager from HVAC program
class SuitManager {
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

    //Static initialization blocks
    //Map internal values for suits to words
    static {
        suits = new HashMap<Integer, String>();
        suits.put(SPADES,"spades");
        suits.put(HEARTS,"hearts");
        suits.put(DIAMONDS,"diamonds");
        suits.put(CLUBS,"clubs");
    }

    //Map internal values for suits to glyph strings
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
