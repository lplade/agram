package name.lplade;

import java.util.LinkedList;
import name.lplade.SuitManager.Suit; //Suit enum

class Trick {
    //represents the little pile of cards on the table during a given round

    private LinkedList<Card> pile = new LinkedList<>();
    private Suit suit; //the suit used for scoring the trick
    private int highVal; //the best value in this trick

    //TODO re-write so we can initialize with undefined values, then we don't need a special case for first round
    Trick(Card firstCard){ //create by passing the first card in the round
        this.pile.add(firstCard);
        this.suit = firstCard.getSuitEnum();
        this.highVal = firstCard.getValue();
        //remember to treat the starting player as the round leader
    }

    public Suit getSuit(){
        return this.suit;
    }


    public int getHighValue(){
        return this.highVal;
    }

    boolean addCard(Card newCard){
        //TEST FOR LEGAL PLAYS BEFORE CALLING THIS
        boolean leading = false;
        this.pile.add(newCard);
        if(newCard.getSuitEnum() == this.suit){
            if(newCard.getValue() > this.highVal){
                this.highVal = newCard.getValue();
                leading = true;
            }
        }
        return leading; //returns a flag to tell us the card puts us in the lead
    }

    boolean isLegal(Card newCard, Player player){
        // if there are ANY cards matching lead suit, then non-matching suits are illegal
        boolean legit = true;
        Suit newCardSuit = newCard.getSuitEnum();
        if (newCardSuit != this.suit) {
        //loop through the hand and see if we have any matching cards we COULD play
            for (Card card : player.getHand().getCards()) {
                if (card != newCard && card.getSuitEnum() == this.suit) {
                    //don't test card against itself. fail if we've got a valid play.
                    legit = false;
                }
            }
        }
        return legit;
    }

    @Override
    public String toString() {
        //returns a list of glyphs representing the current pile
        String cardList = "";
        for (Card c : this.pile) {
            cardList = cardList + c.getCardGlyph() + " ";
        }
        return cardList;
    }




}
