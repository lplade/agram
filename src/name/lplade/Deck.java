package name.lplade;

import java.util.Collections;
import java.util.LinkedList;

/**
 * Created by lade on 10/18/16.
 */
public class Deck {

    private LinkedList<Card> deck = new LinkedList<>();

    //This generates the special 35-card Agram deck

    Deck(){
        //
        int[] suits = { 0xA0, 0xB0, 0xC0, 0xD0 }; //TODO use SuitManager?
        for(int s=0; s < 4; s++){ //each suit
            for(int val=1; val <= 10; val++) { //ace, 2, 3 ... 10)
                if (!(val == 1 && s == 0)) { //don't add "the chief"
                    Card cardToAdd = new Card(suits[s], val);
                    this.deck.add(cardToAdd);
                }
            }
        }
        assert this.deck.size() == 35;

        //can't imagine a use case for an unshuffled deck, let's shuffle on construction
        Collections.shuffle(this.deck);
    }

    //in case we ever need to re-shuffle?
    void shuffle(){
        Collections.shuffle(this.deck);
    }

    public Card deal(){
        Card topCard = this.deck.pop();
        return topCard;
    }

    @Override
    public String toString() {
        //this is mostly for testing, unlikely it will ever be needed for game
        String cardList = "";
        for (int i = 0; i < this.deck.size(); i++) {
            cardList = cardList + this.deck.get(i).getString() + " ";
        }
        return cardList;
    }
}
