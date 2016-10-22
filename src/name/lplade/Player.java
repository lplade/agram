package name.lplade;

import java.util.ArrayList;

/**
 * Created by lade on 10/18/16.
 */
public class Player {

    String name;
    Hand hand;

    Player(String name){
        this.name = name;
        this.hand = new Hand(); //leave empty, deal in game
    }


    private class Hand {
        ArrayList<Card> cardsInHand = new ArrayList<>();

        private Hand(){
            //this.cardsInHand = null;
        }

        //TODO sort hands in a consistent way every time they are displayed

        private void add(Card cardToAdd){
            this.cardsInHand.add(cardToAdd);
        }

        private void hideHand() {
            //sets all cards in hand to "face down"
            for (int i = 0; i < this.cardsInHand.size(); i++) {
                Card cardToHide = this.cardsInHand.get(i);
                cardToHide.setFaceDown(true);
                this.cardsInHand.set(i, cardToHide);
            }
        }

        @Override
        public String toString(){
            // returns picture glyphs
            String handList = "";
            for (int i = 0; i < this.cardsInHand.size(); i++) {
                handList = handList + this.cardsInHand.get(i).getCardGlyph() + " ";
            }
            return handList;
        }


    }

    //TODO subclass adding properties for CPU players

}
