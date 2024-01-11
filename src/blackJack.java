import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;

/**
 * This Java program represents a simplified implementation of the classic Blackjack game.
 * Players are dealt initial cards and can choose to "hit" or "stand" to achieve a hand value
 * as close to 21 as possible without exceeding it. The game includes features such as
 * Blackjack recognition, bust conditions, and determines the winner based on hand values.
 * The console-based interface allows users to interact and make decisions throughout the game.
 */
public class blackJack {

    // Instance variables
    private ArrayList<String> cards;
    private ArrayList<String> dealerDeck;
    private ArrayList<String> playerDeck;

    private int counter;
    private boolean userStatus;

    /**
     * Constructor for the Blackjack game.
     * Initializes the game state and deals initial cards to the player and dealer.
     */
    blackJack() {
        this.userStatus = true;
        this.counter = 52;
        this.cards = new ArrayList<>();
        this.dealerDeck = new ArrayList<>();
        this.playerDeck = new ArrayList<>();
        initializeCards();
        addPlayerDeck();
        addPlayerDeck();
        addDealerDeck();
        addDealerDeck();
    }

    /**
     * Initializes the deck of cards with standard values.
     */
    void initializeCards() {
        String[] suits = {"Diamond", "Club", "Spade", "Heart"};
        String[] faceCards = {"King", "Queen", "Jack", "Ace"};
        for (String i : suits) {
            // Loop for numbered cards (2 to 10)
            for (int j = 2; j < 11; j++) {
                this.cards.add(i + " " + j);
            }
            // Loop for face cards (King, Queen, Jack, Ace)
            for (String k : faceCards) {
                this.cards.add(i + " " + k);
            }
        }
        System.out.println(getCards());
    }

    /**
     * Starts the game loop, allowing the player to hit or stand.
     */
    void gameOn() {
        Scanner scan = new Scanner(System.in);
        while (this.userStatus) {
            System.out.println("Dealers Deck: ");
            printDealerDeck();
            System.out.println("Player Deck: ");
            for (String i : this.getPlayerDeck()) {
                System.out.println(i);
            }
            // Check for Blackjack
            if (getTotalValue(getPlayerDeck()) == 21) {
                System.out.println("BlackJack!!\nYou win!!!");
                result();
                break;
            }
            System.out.println("Do you wish to hit(h) or stand(s)?\n==>");
            String userInput = scan.nextLine();
            cardChecker(userInput);
        }
    }

    /**
     * Checks the user input and performs actions accordingly (hit or stand).
     *
     * @param input User input (h for hit, s for stand).
     */
    void cardChecker(String input) {
        int totalValue = 0;
        if (input.equals("h")) {
            System.out.println("Cards distributed!");
            addPlayerDeck();
            // Check for Bust
            if (getTotalValue(getPlayerDeck()) > 21) {
                System.out.println("Bust!!!");
                result();
            } else {
                gameOn();
            }
        } else if (input.equals("s")) {
            // Check for Win, Push, or Loss
            if (getTotalValue(getPlayerDeck()) < 21 && getTotalValue(getPlayerDeck()) > getTotalValue(getDealerDeck())) {
                System.out.println("You win!!!!");
                result();
            } else if(getTotalValue(getPlayerDeck()) == getTotalValue(getDealerDeck())) {
                System.out.println("It's a push!!");
                result();
            } else {
                System.out.println("You lost!");
                result();
            }
        }
    }

    /**
     * Returns the numerical value of a card.
     *
     * @param card The card whose value needs to be determined.
     * @return The numerical value of the card.
     */
    int getValue(String card) {
        int[] numbers = {2, 3, 4, 5, 6, 7, 8, 9};
        String[] suits = {"Diamond", "Club", "Spade", "Heart"};
        String[] faceCards = {"King", "Queen", "Jack", "Ace"};
        int value = 0;
        // Check for face cards and Ace
        for (String i : faceCards) {
            if (card.contains(i)) {
                value = 10;
            } else if (card.contains("Ace")) {
                value = 11;
                break;
            }
        }
        // Check for numbered cards
        for (int i : numbers) {
            if (card.contains(String.valueOf(i))) {
                value = i;
                break;
            }
        }
        return value;
    }

    /**
     * Calculates the total value of a deck of cards.
     *
     * @param list The deck of cards.
     * @return The total value of the deck.
     */
    int getTotalValue(ArrayList<String> list) {
        int totalValue = 0;
        for (String i : list) {
            totalValue += getValue(i);
        }
        return totalValue;
    }

    /**
     * Sets the userStatus variable to false, ending the game.
     */
    void flipBoolean() {
        this.userStatus = false;
    }

    /**
     * Prints the dealer's deck, revealing only the first card.
     */
    void printDealerDeck() {
        for (String i : this.getDealerDeck()) {
            // Print only the first card, hide others
            if (getDealerDeck().indexOf(i) == 0) {
                System.out.println(i);
            } else {
                System.out.println("[Card]");
            }
        }
    }

    /**
     * Picks a random card from the deck and returns it.
     *
     * @return A randomly picked card.
     */
    String pickRandom() {
        Random random = new Random();
        int pick = random.nextInt(0, this.counter);
        decrementCard();
        String card = this.cards.get(pick);
        removeCard(pick);
        return card;
    }
    void result(){
        // Display results
        System.out.println("Your hand: " + getPlayerDeck() + "\nYour hand value: " + getTotalValue(getPlayerDeck())
                + "\n" + "Dealers hand:" + getDealerDeck()+ "\nDealers hand value: " + getTotalValue(getDealerDeck()));
        flipBoolean();
    }

    /**
     * Decrements the card counter.
     *
     * @return The decremented card counter value.
     */
    int decrementCard() {
        return this.counter--;
    }

    /**
     * Adds a card to the player's deck.
     */
    void addPlayerDeck() {
        this.getPlayerDeck().add(pickRandom());
    }

    /**
     * Adds a card to the dealer's deck.
     */
    void addDealerDeck() {
        this.getDealerDeck().add(pickRandom());
    }

    /**
     * Removes a card from the deck at the specified index.
     *
     * @param index The index of the card to be removed.
     */
    void removeCard(int index) {
        this.cards.remove(index);
    }

    /**
     * Gets the deck of cards.
     *
     * @return The deck of cards.
     */
    ArrayList<String> getCards() {
        return this.cards;
    }

    /**
     * Gets the dealer's deck.
     *
     * @return The dealer's deck.
     */
    ArrayList<String> getDealerDeck() {
        return this.dealerDeck;
    }

    /**
     * Gets the player's deck.
     *
     * @return The player's deck.
     */
    ArrayList<String> getPlayerDeck() {
        return this.playerDeck;
    }
}
