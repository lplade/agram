package name.lplade;

import java.util.Scanner;
import java.util.concurrent.ThreadLocalRandom;

import name.lplade.SuitManager.Suit; //Suit enum



public class Main {

    public static void main(String[] args) {

        final Card CHIEF = new Card(Suit.SPADES, 1);
        char[] cbraw = Character.toChars(0x1F0A0);
        final String CARDBACK = new String(cbraw); //convert high Unicode into displayable string



        Scanner numScanner = new Scanner(System.in);
        //TODO get all inputs as string and convert to int, then pull numScanner
        Scanner strScanner = new Scanner(System.in);

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
            while(true){
                System.out.println("Enter the name for player " + (p+1) + ": "); //normal person counting
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
            System.out.printf("%1d  %s\n", (p+1), playerNames[p] );
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
        for (int t = 0; t < (numPlayers * 2); t++){
            System.out.print("Dealing three cards to " + table.getCurrentPlayer().getName() + "... ");
            for (int i = 0; i < 3; i++) {
                //insert a tiny pause so it feels more like actually dealing cards
                //http://stackoverflow.com/questions/24104313/how-to-delay-in-java
                try {
                    Thread.sleep(300);
                } catch(InterruptedException ex) {
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
        for (int p = 0; p < numPlayers; p++){
            assert ( table.getPlayer(p).getHand().getLength() == 6 ) : table.getPlayer(p).getName();
        }


        //reset the counter, and start play to the dealer's right
        table.startNewRound(firstPlayer);
        table.advancePlay();
        System.out.println();


        //*** main game loop ***


        //Play six rounds
        for (int round = 0; round < 6; round++){
            //Each player plays one card in the round
            for (int p = 0; p < numPlayers; p++){
                clearScreen();
                System.out.println("BEGIN TURN FOR " + table.getCurrentPlayer().getName().toUpperCase());
                System.out.println("All other players look away! Press <ENTER> to continue");
                String enter = strScanner.nextLine();

                //Feed the ArrayList of cards into an array of console-friendly selections
                int cardIndex = 0;
                Card[] cardSelections = new Card[table.getCurrentPlayer().getHand().getLength()];
                for (Card card : table.getCurrentPlayer().getHand().getCards()){
                    cardSelections[cardIndex] = card;
                    cardIndex++;
                }

                //TODO display the cards on the table here

                //Show a list of the cards in the hand, keyed to integers
                System.out.println("-- Your hand --");
                for (int i = 0; i < cardSelections.length; i++){
                    System.out.printf("%1d: %1s ", i+1, cardSelections[i].getCardGlyph());

                }
                System.out.println();


                System.out.println("And then I prompt you");

                //TODO - show hand
                //TODO - offer input choices for eligible cards
                //TODO - pull that card out of player's hand
                //TODO - put that card into center pile
                //Move to the next player's go
                table.advancePlay();

            }
            //TODO score trick

            //TODO starting with player who took trick, repeat above
        }

        //TODO declare winner



    }

    private static void clearScreen(){
        //method to clear the screen
        //current method is based on raw ANSI codes
        //TODO re-write for better system independence

        //http://stackoverflow.com/questions/10241217/how-to-clear-console-in-java
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

}
