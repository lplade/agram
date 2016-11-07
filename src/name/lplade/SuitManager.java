package name.lplade;

//class to encode suits

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
}
