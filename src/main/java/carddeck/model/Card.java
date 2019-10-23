package carddeck.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Card {

    // This class is made immutable with private constructor to ensure identity is same equality
    private final Rank rank;
    private final Suit suit;

    private static final Card[] ALL_CARDS = createAllCards();

    private static Card[] createAllCards() {
        final Card[] all_cards = new Card[Rank.values().length * Suit.values().length];
        int idx = 0;
        for (Rank r : Rank.values()) {
            for (Suit s : Suit.values()) {
                all_cards[idx++] = new Card(r, s);
            }
        }
        return all_cards;
    }

    private Card(Rank rank, Suit suit) {
        this.rank = rank;
        this.suit = suit;
    }

    public static Card getCard(Rank rank, Suit suit) {
        return ALL_CARDS[rank.ordinal() * Suit.values().length + suit.ordinal()];
    }

    public final Rank getRank() {
        return rank;
    }

    public final Suit getSuit() {
        return suit;
    }

    public static List<Card> newDeck() {
        return new ArrayList<>(Arrays.asList(ALL_CARDS));
    }

    public String toString() {
        return rank.toString() + " of " + suit.toString();
    }
}





