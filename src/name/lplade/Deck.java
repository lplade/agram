package name.lplade;

import java.util.LinkedList;

/**
 * Created by lade on 10/18/16.
 */
public class Deck {

    LinkedList<Card> deck = new LinkedList<>();

    //This generates the special 35-card Agram deck

    Deck(){
        int[] suits = { 0xA0, 0xB0, 0xC0, 0xD0 }; //TODO use SuitManager?
        for(int s=0; s < 4; s++){ //each suit
            for(int val=1; val <= 10; val++) { //ace, 2, 3 ... 10)
                Card cardToAdd = new Card(val, s);
            }
        }
    }

}
