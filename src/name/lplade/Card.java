package name.lplade;

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

    public String getString(){
        //return something like "9♣"
        //TODO make hearts and diamonds red?
        String cardString = NumberManager.getNumberStringShort(this.value) + SuitManager.getSuitGlyph(this.suit);
        return cardString;
    }

    //TODO getCardGlyph to return single Unicode glyph representing the card.

    //returns number that appears on face of card, 2 through A;
    public String getNumberStr(){
        String number = NumberManager.getNumberString(this.value);
        return number;
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
        String suit = SuitManager.getSuitString(this.suit);
        return suit;
    }

    //return symbol for suit
    public String getSuitGlyph(){
        String suit = SuitManager.getSuitGlyph(this.suit);
        return suit;
    }

    public boolean isFaceDown() {
        return faceDown;
    }

    public void setValue(String suit) {


        this.value = value;
    }

    public void setSuit(int suit) {
        this.suit = suit;
    }

    public void setFaceDown(boolean faceDown) {
        this.faceDown = faceDown;
    }





}
