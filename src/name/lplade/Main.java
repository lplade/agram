package name.lplade;

import java.util.Scanner;
import java.util.concurrent.ThreadLocalRandom;

import name.lplade.SuitManager.Suit; //Suit enum


public class Main {

    //set up keyboard scanner
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
            numPlayers = inputInt();

            //we run out of cards if we have 6 or more players
            if (numPlayers > 1 && numPlayers < 6) {
                break;
            }
            System.out.println("Please enter a number between 2 and 5.");
            //re-enter...
        }

        //get some player names
        boolean okay;
        String[] playerNames;
        do {
            playerNames = new String[numPlayers];
            for (int p = 0; p < numPlayers; p++) {

                System.out.println("Enter the name for player " + (p + 1) + ": "); //normal person counting
                String name = inputString();
                if (name.isEmpty()) {
                    playerNames[p] = "Player " + (p + 1); //default value for the lazy
                } else {
                    playerNames[p] = name;
                }
            }

            //TODO add a way to designate some players as CPU controlled


            System.out.println("#  Player");
            for (int p = 0; p < numPlayers; p++) {
                System.out.printf("%1d  %s\n", (p + 1), playerNames[p]);
            }
            System.out.println();
            System.out.print("Does that look okay? (Y/N) ");
            okay = inputYN();
            System.out.println();

        } while (! okay);

        Game table = new Game(playerNames);

        Deck deck = new Deck();
        System.out.println("Removing the \"Chief\"...");
        delay(200);
        System.out.println(CHIEF.getCardGlyph());
        System.out.println();
        delay(200);
        System.out.println("Here is the Agram deck:");
        System.out.println(deck.toString2());
        System.out.println();
        System.out.println("Remember, aces are high");
        System.out.println();
        System.out.println("Win tricks by playing the highest card matching the leading suit.");
        System.out.println("Winner of each trick gets to lead the next round.");
        System.out.println("Winner of the sixth round wins the game!");
        System.out.println();
        //Let's just pick one player at random to deal first.
        //Generate a random index between 0 and the number of players
        int firstPlayer = ThreadLocalRandom.current().nextInt(0, numPlayers);
        //System.out.println("(Selected player " + firstPlayer);
        assert (firstPlayer >= 0 && firstPlayer < (table.getPlayerCount())): numPlayers;

        //initialize the round at that index
        table.startNewRound(firstPlayer);

        System.out.print(playerNames[firstPlayer] + " shuffles... ");
        //insert a tiny pause
        delay(2000);
        deck.shuffle();
        System.out.println("done.");

        table.advancePlay(); //start dealing to NEXT player;
        //treat dealing like two go-rounds
        for (int t = 0; t < (numPlayers * 2); t++) {
            System.out.print("Dealing three cards to " + table.getCurrentPlayer().getName() + "... ");
            for (int i = 0; i < 3; i++) {
                //insert a tiny pause so it feels more like actually dealing cards
                delay(300);

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

        System.out.println("Press <Enter> to begin play");
        inputEnter();


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
            inputEnter();
            clearScreen();

            //run the remaining players
            for (int p = 1; p < numPlayers; p++) {
                //Move to the next player's go
                table.advancePlay();

                //play a card, and flags winner if necessary
                boolean winner = remainingPlayers(table, trick);
                if (winner){
                    table.setWinning();
                    System.out.println("You are winning right now.");
                } else {
                    System.out.println("That card didn't help you.");
                }
                System.out.println("Press <ENTER> when ready to continue");
                inputEnter();
                clearScreen();
            }
            clearScreen();

            System.out.println("You can call everyone back to look at the screen!");
            System.out.println();
            System.out.println("SUMMARY");
            System.out.println(trick.toString());

            System.out.println(table.getWinningPlayer().getName() + " played the highest " + SuitManager.getSuitString(trick.getSuit()) + ", so");
            if (round < 5) {
                System.out.println(table.getWinningPlayer().getName() + " takes the trick and leads the next round.");
                //start the new round with that player
                table.startNewRound(table.getWinnerIndex());
                System.out.println("Press <Enter> to continue");
                inputEnter();
            } else {
                System.out.println(table.getWinningPlayer().getName() + " wins the game of Agram!");
            }

        }
        //TODO build in a play again loop

        //Close the scanner
        strScanner.close();
        System.exit(0);
    }

    // *** internal methods ***

    private static boolean inputYN() {
        //looks for a Y or N value, converts to a boolean

        boolean yes = false;
        boolean reenter;
        do {
            reenter = false;
            String input = strScanner.nextLine();
            if (input.toUpperCase().startsWith("Y")) {
                yes = true;
            } else if (input.toUpperCase().startsWith("N")) {
                yes = false;
            } else {
                reenter = true;
                System.out.println("Please enter Y or N");
            }
        } while (reenter);

        return yes;
    }

    private static void inputEnter() {
        //wait until we press enter, ignores any input);
        strScanner.nextLine();

    }

    private static String inputString() {
        //string input scanner;
        String input = strScanner.nextLine();
        return input;
    }

    private static int inputInt() {
        //integer input scanner with validation
        int input = -1;
        String inputStr = "FAIL";
        boolean badInt = true;
        while(badInt) {
            inputStr = strScanner.nextLine();
            try {
                badInt = false;
                input = Integer.parseInt(inputStr);
            } catch (NumberFormatException e) {
                badInt = true;
                System.out.println("Please enter a valid number!");
            }
        }
        assert (! inputStr.equals("FAIL")) : inputStr;
        assert (input >= 0) : input;
        return input;
    }

    private static Card firstPlayer(Game table){
        //Handling for first player in round is unique since Trick isn't initialized yet

        clearScreen();
        System.out.println("                         ***NEW ROUND***");
        System.out.println();
        System.out.println("                       BEGIN TURN FOR " + table.getCurrentPlayer().getName().toUpperCase());
        System.out.println();
        System.out.println("All other players look away! Press <ENTER> to continue");
        inputEnter();

        clearScreen();

        table.getCurrentPlayer().getHand().sort(); //sort the cards so it's easier to understand what you have

        //Feed the ArrayList of cards into an array of console-friendly selections
        int cardIndex = 0;
        Card[] cardSelections = new Card[table.getCurrentPlayer().getHand().getLength()];
        for (Card card : table.getCurrentPlayer().getHand().getCards()) {
            cardSelections[cardIndex] = card;
            cardIndex++;
        }

        System.out.println();
        System.out.println("-- Cards in play -- ");
        System.out.println("     none yet"); //first round
        System.out.println("You may select the card to lead this trick.");
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
            selection = inputInt() - 1;

            if (selection >= 0 && selection < table.getCurrentPlayer().getHand().getLength()) {
                legit = true;

            } else {
                System.out.println("Please enter a valid number.");
            }
        } while (!legit);

        //note that this destroys the card in the player's hand, so make sure to put the returned value in the new Trick
        return table.getCurrentPlayer().getHand().moveFrom(cardSelections[selection]);
    }

    private static boolean remainingPlayers(Game table, Trick trick){
        clearScreen();
        //re-show current trick so players can see what's going on.
        System.out.println();
        System.out.println("-- Cards in play -- ");
        System.out.println(trick.toString());
        System.out.println();
        System.out.println("                       BEGIN TURN FOR " + table.getCurrentPlayer().getName().toUpperCase());
        System.out.println();
        System.out.println("All other players look away! Press <ENTER> to continue");
        inputEnter();

        clearScreen();

        table.getCurrentPlayer().getHand().sort(); //sort the cards so it's easier to understand what you have

        //Feed the ArrayList of cards into an array of console-friendly selections
        int cardIndex = 0;
        Card[] cardSelections = new Card[table.getCurrentPlayer().getHand().getLength()];
        for (Card card : table.getCurrentPlayer().getHand().getCards()) {
            cardSelections[cardIndex] = card;
            cardIndex++;
        }

        System.out.println();
        System.out.println("-- Cards in play -- ");
        System.out.println(trick.toString());

        String winningValue = trick.getHighValueStr();
        String winningSuit = trick.getSuitStr();
        System.out.printf("Beat %s of %s to win trick\n", winningValue, winningSuit);
        System.out.println();

        //Show a list of the cards in the hand, keyed to integers
        System.out.println("-- " + table.getCurrentPlayer().getName() + "'s hand --");
        System.out.println(table.getCurrentPlayer().getHand());

        for (int i = 0; i < cardSelections.length; i++) {
            if (trick.isLegal(cardSelections[i], table.getCurrentPlayer()) && trick.wouldWin(cardSelections[i])) {
                System.out.printf("%1d: %-16s    <- this would beat the current card\n", i + 1, cardSelections[i].getLongString());
            }
            else if (trick.isLegal(cardSelections[i], table.getCurrentPlayer())){
                System.out.printf("%1d: %s\n", i + 1, cardSelections[i].getLongString());
            } else {
                System.out.printf("   %-16s         <- not a valid play\n", cardSelections[i].getLongString()); //invalid selections eliminated
            }

        }

        System.out.println();

        //Get input
        System.out.println("Select a card (1-" + table.getCurrentPlayer().getHand().getLength() + "):");
        int selection;
        boolean legit = false;

        do {
            selection = inputInt() - 1;
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
        boolean winning = trick.addCard(table.getCurrentPlayer().getHand().moveFrom(cardSelections[selection]));

        //re-show current trick so player can see what's going on.
        System.out.println();
        System.out.println("-- Cards in play -- ");
        System.out.println(trick.toString());

        //note that this returns a boolean which tells main if this player is now winning
        return winning;
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

    private static void delay(int ms) {
        //lets us delay a bit where it seems appropriate
        //http://stackoverflow.com/questions/24104313/how-to-delay-in-java
        try {
            Thread.sleep(ms);
        } catch (InterruptedException ex) {
            Thread.currentThread().interrupt();
        }
    }


}
