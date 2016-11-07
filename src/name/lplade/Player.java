package name.lplade;

import java.util.ArrayList;
import java.util.Iterator;

import static name.lplade.NumberManager.ACE;

class Player {

    private String name;
    private Hand hand;
    private boolean myTurn = false;

    //Constructor
    Player(String name){
        this.name = name;
        this.hand = new Hand(); //leave empty, deal in game
    }

    //Getters and setters
    String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    Hand getHand() {
        return hand;
    }

    public void setHand(Hand hand) {
        this.hand = hand;
    }

    void addToHand(Card cardToAdd){
        this.hand.add(cardToAdd);
    }

    public Card pullFromHand(Card cardToPull){
        this.hand.moveFrom(cardToPull);
        return cardToPull; //if we already know the value of cardToPull, is this redundant?
    }

    public boolean isMyTurn() {
        return myTurn;
    }

    void setMyTurn(boolean myTurn) {
        this.myTurn = myTurn;
    }


    @Override
    public String toString(){
        return name;
        //TODO return other info?
    }

    // Hands only ever exists inside Player, so define here

    class Hand {
        private ArrayList<Card> cardsInHand = new ArrayList<>();

        Hand(){
            //this.cardsInHand = null;
        }

        void sort(){
            // https://courses.cs.vt.edu/csonline/Algorithms/Lessons/SimpleCardSort/index.html
            Hand tempHand = new Hand();
            for (SuitManager.Suit suit : SuitManager.Suit.values()) {
                //System.out.println("Sorting " + suit.toString());
                int[] displaySequence = { 3, 4, 5, 6, 7, 8, 9, 10, ACE}; //TODO expand NumberManager to help with this?
                for (int value : displaySequence) {
                    //System.out.println("  Sorting " + NumberManager.getNumberString(value));
                    //need to use "iterator" form to remove from a list we are looping through!
                    for (Iterator<Card> iterator = this.cardsInHand.iterator(); iterator.hasNext(); ) {
                        Card card = iterator.next();
                        if (card.getValue() == NumberManager.getNumberValue(value) && card.getSuitEnum() == suit) {
                            //System.out.println("SORT: Moving " + card.getString());
                            tempHand.add(card); //move the card into the new "hand"
                            iterator.remove(); //destroy the card in the old hand
                        }
                    }
                }
            }
            //once sorted, replace our original hand with this sorted hand
            this.cardsInHand = tempHand.cardsInHand;

        }

        private void add(Card cardToAdd){
            this.cardsInHand.add(cardToAdd);
        }

        Card moveFrom(Card cardToPull){
            this.cardsInHand.remove(cardToPull);
            return cardToPull;
        }

        private void hideHand() {
            //sets all cards in hand to "face down"
            for (int i = 0; i < this.cardsInHand.size(); i++) {
                Card cardToHide = this.cardsInHand.get(i);
                cardToHide.setFaceDown(true);
                this.cardsInHand.set(i, cardToHide);
            }
        }

        int getLength() {
            return cardsInHand.size();
        }

        ArrayList<Card> getCards(){
            return this.cardsInHand;
        }

        @Override
        public String toString(){
            // returns picture glyphs
            String handList = "";
            for (Card aCardsInHand : this.cardsInHand) {
                handList = handList + aCardsInHand.getCardGlyph() + " ";
            }
            return handList;
        }



    }

    //TODO subclass adding properties for CPU players

}
