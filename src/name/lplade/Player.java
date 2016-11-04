package name.lplade;


import java.util.ArrayList;

class Player {

    private String name;
    private Hand hand;
    private Player rightPlayer; //must add next player in list
    private Player leftPlayer; //must add previous player in list

    //Constructor
    Player(String name){
        this.name = name;
        this.hand = new Hand(); //leave empty, deal in game
        this.rightPlayer = null;
        this.leftPlayer = null;
    }

    //Getters and setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    void setRightPlayer(Player p) {
        rightPlayer = p;
    }

    void setLeftPlayer(Player p) {
        leftPlayer = p;
    }

    Player getRightPlayer(){
        assert rightPlayer != null;
        return rightPlayer;
    }
    Player getLeftPlayer(){
        assert rightPlayer != null;
        return leftPlayer;
    }

    public Hand getHand() {
        return hand;
    }

    public void setHand(Hand hand) {
        this.hand = hand;
    }

    public void addToHand(Card cardToAdd){
        this.hand.add(cardToAdd);
    }
    public Card pullFromHand(Card cardToPull){
        this.hand.moveFrom(cardToPull);
        return cardToPull; //if we already know the value of cardToPull, is this redundant?
    }

    private class Hand {
        private ArrayList<Card> cardsInHand = new ArrayList<>();

        Hand(){
            //this.cardsInHand = null;
        }

        //TODO sort hands in a consistent way every time they are displayed

        private void add(Card cardToAdd){
            this.cardsInHand.add(cardToAdd);
        }

        private Card moveFrom(Card cardToPull){
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
