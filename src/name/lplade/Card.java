package name.lplade;


import name.lplade.SuitManager.Suit; //need the Suit enum

class Card {

    //Cards needed are A, 10, 9, 8, 7, 6, 5, 4, 3
    //Ace is high - give numeric value of 11.

    //Ways we might want to use data about cards
    //- strings we can build into descriptions, e.g. "ace of spades"
    //- short symbols for when we're displaying a bunch of cards "9♣"
    //- numeric values to test if aces are better than twos

    private int value; // 1 means ace here
    //private int suit; //0xA0, 0xB0, 0xC0, 0xD0 - see SuitManager()
    private Suit suit;
    private boolean isRed;
    private boolean faceDown; //for display purposes

    Card(Suit suit, int value) {
        this.suit = suit;
        this.value = value;
        this.faceDown = false;
        this.isRed = (suit == Suit.DIAMONDS) || (suit == Suit.HEARTS);
    }

    // ** GETTERS **

    String getString(){
        //return something like "9♣"
        //TODO make hearts and diamonds red?
        String numPart = NumberManager.getNumberStringShort(this.value);
        String symPart = Character.toString(SuitManager.getSuitGlyph(this.suit));
        return numPart + symPart;
    }

    String getCardGlyph(){
        //Unicode 6.0 card symbols
        int cardGlyphRaw = (0x1f000 + SuitManager.getUnicodeMask(this.suit) + this.value);
        //Turns out these high Unicode values don't fit in a single char.
        // Have to turn this into an array of chars, then turn that into a string
        char[] ca = Character.toChars(cardGlyphRaw);
        String cardGlyphPlain = new String(ca);
        String cardGlyph;
        if (this.isRed) {
            cardGlyph = redIt(cardGlyphPlain);
        } else {
            cardGlyph = blacken(cardGlyphPlain);
        }
        return cardGlyph;
    }

    //TODO getCardGlyph to return single Unicode glyph representing the card.

    //returns number that appears on face of card, 2 through A;
    public String getNumberStr(){
        return NumberManager.getNumberString(this.value);
    }

    //returns numeric value for in-game comparisons
    public int getValue(){
        int value = NumberManager.getNumberValue(this.value);

        //complain if value is somehow outside of expected range
        assert value >= 2 && value <= 11 : value;
        return value;
    }

    //return suit as a word "diamonds" etc.
    public String getSuitWord(){
        return SuitManager.getSuitString(this.suit);
    }

    //return symbol for suit
    public String getSuitGlyph(){
        return Character.toString(SuitManager.getSuitGlyph(this.suit));
    }

    public boolean isFaceDown() {
        return faceDown;
    }

    // END GETTERS

    // ** SETTERS **

    //can't think of good use for having setters for suit and value
    //cards work like cards, not magic

    void setFaceDown(boolean faceDown) {
        this.faceDown = faceDown;
    }

    // END SETTERS

    // ** MISCELLANEOUS METHODS **

    private String redIt(String makeMeRed) {
        final String ANSI_reset_color = "\u001B[0m";
        final String RED_TEXT = "\u001B[31m";
        //ANSI 'white' background is usually more like a gray, no reliable way to make it white. :(
        final String WHITE_BACK = "\u001B[47m";
        return WHITE_BACK + RED_TEXT + makeMeRed + ANSI_reset_color;
    }

    private String blacken(String toBlack) {
        final String ANSI_reset_color = "\u001B[0m";
        final String BLACK_TEXT = "\u001B[30m";
        final String WHITE_BACK = "\u001B[47m";
        return WHITE_BACK + BLACK_TEXT + toBlack + ANSI_reset_color;
    }

}
