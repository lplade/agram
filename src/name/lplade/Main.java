package name.lplade;

import java.util.Scanner;
import java.util.concurrent.ThreadLocalRandom;

import name.lplade.SuitManager.Suit; //Suit enum


public class Main {

    //set up keyboard scanners
    private static Scanner numScanner = new Scanner(System.in);
    //TODO get all inputs as string and convert to int, then pull numScanner
    private static Scanner strScanner = new Scanner(System.in);

    private static final Card CHIEF = new Card(Suit.SPADES, 1);

    private static char[] cbraw = Character.toChars(0x1F0A0);
    private static final String CARDBACK = new String(cbraw); //convert high Unicode into displayable string
    //For some reason, this only seems to work as multiple statements, and not as a method

    public static void main(String[] args) {

        System.out.println("B i e n v e n u e   à   A G R A M");

        int numPlayers;

        while (true) {
            System.out.print("How many players? ");
            numPlayers = numScanner.nextInt(); //TODO better input error handling

            //we run out of cards if we have 6 or more players
            if (numPlayers > 1 && numPlayers < 6) {
                break;
            }
            System.out.println("Please enter a number between 2 and 5.");
            //re-enter...
        }

        //get some player names
        String[] playerNames = new String[numPlayers];
        for (int p = 0; p < numPlayers; p++) {
            while (true) {
                System.out.println("Enter the name for player " + (p + 1) + ": "); //normal person counting
                String name = strScanner.nextLine();
                if (name.isEmpty()) { //make sure we actually entered a name before continuing
                    System.out.println("Name can't be blank!");
                } else {
                    playerNames[p] = name;
                    break; //resume
                }
            }
        }

        //TODO add a way to designate some players as CPU controlled

        //This is just for testing:
        System.out.println("#  Player");
        for (int p = 0; p < numPlayers; p++) {
            System.out.printf("%1d  %s\n", (p + 1), playerNames[p]);
        }

        //TODO offer some kind of confirmation here in case of mistakes

        Game table = new Game(playerNames);

        //Let's just pick one player at random to deal first.
        //Generate a random index between 0 and the number of players
        int firstPlayer = ThreadLocalRandom.current().nextInt(0, numPlayers);
        assert (firstPlayer >= 0 && firstPlayer < (table.getPlayerCount() - 1));

        //initialize the round at that index
        table.startNewRound(firstPlayer);

        System.out.print(playerNames[firstPlayer] + " shuffles... ");
        Deck deck = new Deck();
        System.out.println("done.");

        table.advancePlay(); //start dealing to NEXT player;
        //treat dealing like two go-rounds
        for (int t = 0; t < (numPlayers * 2); t++) {
            System.out.print("Dealing three cards to " + table.getCurrentPlayer().getName() + "... ");
            for (int i = 0; i < 3; i++) {

                //insert a tiny pause so it feels more like actually dealing cards
                //http://stackoverflow.com/questions/24104313/how-to-delay-in-java
                try {
                    Thread.sleep(300);
                } catch (InterruptedException ex) {
                    Thread.currentThread().interrupt();
                }
                Card newCard = deck.deal();
                //System.out.print(newCard.getString() + " "); //DEBUG
                System.out.print(CARDBACK + " ");
                table.getCurrentPlayer().addToHand(newCard);
            }
            System.out.println();
            //System.out.print("DEBUG: Hand for " + table.getCurrentPlayer().getName() + ": ");
            //System.out.println(table.getCurrentPlayer().getHand().toString());
            table.advancePlay();
        }
        System.out.println();

        //Assert that every play has exactly six cards now
        for (int p = 0; p < numPlayers; p++) {
            assert (table.getPlayer(p).getHand().getLength() == 6) : table.getPlayer(p).getName();
        }


        //reset the counter, and start play to the dealer's right
        table.startNewRound(firstPlayer);
        table.advancePlay();
        System.out.println();

        //**********************
        //*** main game loop ***
        //**********************

        //Play six rounds
        for (int round = 0; round < 6; round++) {
            //Each player plays one card in the round


            // Run the first player's turn and construct the trick with the results
            Trick trick = new Trick(firstPlayer(table));

            System.out.println("Press <ENTER> when ready for next player");
            strScanner.nextLine();
            clearScreen();

            //Move to the next player's go
            table.advancePlay();

            //run the remaining players
            for (int p = 1; p < numPlayers; p++) {
                remainingPlayers(table, trick);

                System.out.println("Press <ENTER> when ready for next player");
                strScanner.nextLine();
                clearScreen();

                //Move to the next player's go
                table.advancePlay();

            }
            System.out.println("Now score!!!!!");
            //TODO score trick

            //TODO starting with player who took trick, repeat above
        }

        //TODO declare winner


    }

    private static Card firstPlayer(Game table){
        //Handling for first player in round is unique since Trick isn't initialized yet

        clearScreen();
        System.out.println("NEW ROUND");
        System.out.println("BEGIN TURN FOR " + table.getCurrentPlayer().getName().toUpperCase());
        System.out.println("All other players look away! Press <ENTER> to continue");
        strScanner.nextLine();

        //Feed the ArrayList of cards into an array of console-friendly selections
        int cardIndex = 0;
        Card[] cardSelections = new Card[table.getCurrentPlayer().getHand().getLength()];
        for (Card card : table.getCurrentPlayer().getHand().getCards()) {
            cardSelections[cardIndex] = card;
            cardIndex++;
        }

        System.out.println();
        System.out.println(CHIEF.getCardGlyph() + " AGRAM"); //just a fun cosmetic nod);
        System.out.println("-- Cards in play -- ");
        System.out.println("none yet"); //first round
        System.out.println();

        //Show a list of the cards in the hand, keyed to integers
        System.out.println("-- " + table.getCurrentPlayer().getName() + "'s hand --");
        System.out.println(table.getCurrentPlayer().getHand());

        for (int i = 0; i < cardSelections.length; i++) {
            System.out.printf("%1d: %s\n", i + 1, cardSelections[i].getLongString());
        }

        System.out.println();

        //Get input
        System.out.println("Select a card (1-" + table.getCurrentPlayer().getHand().getLength() + "):");
        int selection;
        boolean legit = false;

        do {
            selection = numScanner.nextInt() - 1;
            //TODO better input validation
            if (selection >= 0 && selection < table.getCurrentPlayer().getHand().getLength()) {
                legit = true;

            } else {
                System.out.println("Please enter a valid number.");
            }
        } while (!legit);

        //note that this destroys the card in the player's hand, so make sure to put the returned value in the new Trick
        return table.getCurrentPlayer().getHand().moveFrom(cardSelections[selection]);
    }

    private static void remainingPlayers(Game table, Trick trick){
        clearScreen();
        System.out.println("BEGIN TURN FOR " + table.getCurrentPlayer().getName().toUpperCase());
        System.out.println("All other players look away! Press <ENTER> to continue");
        strScanner.nextLine();
        clearScreen();

        //Feed the ArrayList of cards into an array of console-friendly selections
        int cardIndex = 0;
        Card[] cardSelections = new Card[table.getCurrentPlayer().getHand().getLength()];
        for (Card card : table.getCurrentPlayer().getHand().getCards()) {
            cardSelections[cardIndex] = card;
            cardIndex++;
        }

        System.out.println();
        System.out.println(CHIEF.getCardGlyph() + " AGRAM"); //just a fun cosmetic nod);
        System.out.println("-- Cards in play -- ");
        System.out.println(trick.toString());
        System.out.println();

        //Show a list of the cards in the hand, keyed to integers
        System.out.println("-- " + table.getCurrentPlayer().getName() + "'s hand --");
        System.out.println(table.getCurrentPlayer().getHand());

        for (int i = 0; i < cardSelections.length; i++) {
            if (trick.isLegal(cardSelections[i], table.getCurrentPlayer())){
                System.out.printf("%1d: %s\n", i + 1, cardSelections[i].getLongString());
            } else {
                System.out.printf("x: %s\n", cardSelections[i].getLongString()); //invalid selections eliminated
            }

        }

        System.out.println();

        //Get input
        System.out.println("Select a card (1-" + table.getCurrentPlayer().getHand().getLength() + "):");
        int selection;
        boolean legit = false;

        do {
            selection = numScanner.nextInt() - 1;
            //TODO better input validation
            if (selection >= 0
                    && selection < table.getCurrentPlayer().getHand().getLength()
                    && trick.isLegal(cardSelections[selection], table.getCurrentPlayer())) {
                legit = true;

            } else {
                System.out.println("Please enter a valid number.");
            }
        } while (!legit);

        //Move the Card out of hand into Trick
        trick.addCard(table.getCurrentPlayer().getHand().moveFrom(cardSelections[selection]));
    }

    private static void clearScreen() {
        //method to clear the screen


/*
        //this doesn't work very well in different environments

        //http://stackoverflow.com/questions/10241217/how-to-clear-console-in-java
        System.out.print("\033[H\033[2J");
        //System.out.flush();*/

        //more system-independent way, but a little kludgey
        //just make 50 linefeeds to push contents off top of screen
        //easy to cheat by peeking in buffer
        for (int line = 0; line < 50; line++) {
            System.out.println();
        }

    }


}
