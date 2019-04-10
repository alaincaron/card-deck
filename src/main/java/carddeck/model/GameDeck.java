package carddeck.model;

import java.util.Collection;
import java.util.List;

public interface GameDeck {
    void reset();
    void init();
    int getNbDecks();
    void addDecks(int nbDecks, boolean shuffled);
    void shuffle();

    List<Card> getRemainingCards();
    int nbRemainingCards();

    /**
     * Deal specified number of cards. Returns empty list if there are less than nbCardsToDeal remaining in the deck.
     * @param nbCardsToDeal
     * @return True if the number of remaining cards in deck initially was at least nbCardsToDeal.  False otherwise.
     */
    List<Card> dealCards(int nbCardsToDeal);
}
