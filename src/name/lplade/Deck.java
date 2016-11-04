package name.lplade;

import java.util.Collections;
import java.util.LinkedList;
import name.lplade.SuitManager.Suit; //need the Suit enum

class Deck {

    private LinkedList<Card> deck = new LinkedList<>();

    //This generates the special 35-card Agram deck

    Deck(){
        //
        //int[] suits = { 0xA0, 0xB0, 0xC0, 0xD0 }; //TODO use SuitManager? enums?
        // http://stackoverflow.com/questions/1104975/a-for-loop-to-iterate-over-an-enum-in-java
        for(Suit s : Suit.values()){
        //for(int s=0; s < 4; s++){ //each suit
            for(int val=1; val <= 10; val++) { //ace, 2, 3 ... 10)
                if (!(val == 1 && s == Suit.SPADES)) { //don't add "the chief"
                    Card cardToAdd = new Card(s, val);
                    this.deck.add(cardToAdd);
                }
            }
        }
        assert this.deck.size() == 35;

        //can't imagine a use case for an un-shuffled deck, let's shuffle on construction
        Collections.shuffle(this.deck);
    }

    //in case we ever need to re-shuffle?
    void shuffle(){
        Collections.shuffle(this.deck);
    }

    public Card deal(){
        return this.deck.pop();
    }

    @Override
    public String toString() {
        //this is mostly for testing, unlikely it will ever be needed for game
        String cardList = "";
        for (Card c : this.deck) {
            cardList = cardList + c.getString() + " ";
        }
        return cardList;
    }

    public String toString2() {
        //this is mostly for testing, unlikely it will ever be needed for game
        String cardList = "";
        for (Card c : this.deck) {
            cardList = cardList + c.getCardGlyph() + " ";
        }
        return cardList;
    }
}
