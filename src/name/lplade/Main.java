package name.lplade;

import java.util.LinkedList;
import java.util.Scanner;
import name.lplade.SuitManager.Suit; //Suit enum

public class Main {

    public static void main(String[] args) {
        Deck deck = new Deck();

        final Card CHIEF = new Card(Suit.SPADES, 1);

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
            System.out.println("Enter the name for player " + (p+1) + ": ");
            playerNames[p] = strScanner.nextLine();
            //TODO assert playerNames[p] is not empty
        }

        //TODO add a way to designate some players as CPU controlled

        System.out.println("#  Player");
        for (int p = 0; p < numPlayers; p++) {
            System.out.printf("%1d  %s\n", (p+1), playerNames[p] );
        }

        //TODO offer some kind of confirmation here in case of mistakes

        //TODO create Player objects for each player, store in a collection
        //TODO create a Class which defines a circular linked list
        //Let's try it with a normal array
        Player[] players = new Player[numPlayers];

        //TODO determine first dealer

        //TODO for each player, deal out 3 cards
        //TODO for each player, deal out 3 more cards

        //TODO main game loop

        //TODO staring with player to dealer's right:
        //TODO - show hand
        //TODO - offer input choices for eligble cards
        //TODO - pull that card out of player's hand
        //TODO - put that card into center pile
        //TODO repeat for each player, going right

        //TODO score trick

        //TODO starting with player who took trick, repeat above

        //TODO run six rounds

        //TODO declare winner

    }

}
