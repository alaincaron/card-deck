package carddeck.impl;

import carddeck.model.Card;
import carddeck.model.GameDeck;
import carddeck.model.Rank;
import carddeck.model.Suit;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;

import java.io.Serializable;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Random;

class GameDeckImpl implements GameDeck, Serializable {
    private static final int DECK_SIZE = Suit.values().length * Rank.values().length;
    private final List<Card> deck = Lists.newArrayListWithCapacity(DECK_SIZE * 3);

    private int nbDecks = 0;

    @Override
    public void reset() {
        nbDecks = 0;
        deck.clear();

    }

    @Override
    public void init() {
        // initializes a deck with the number of required decks
        deck.clear();
        for (int i = 0; i < nbDecks; ++i) {
            deck.addAll(Card.newDeck());
        }
    }

    @Override
    public int getNbDecks() {
        return nbDecks;
    }

    @Override
    public void addDecks(int nbDecksToAdd, boolean shuffled) {
        if (nbDecksToAdd <= 0) {
            throw new IllegalArgumentException("Invalid nbDecksToAdd: " + nbDecksToAdd);
        }
        if (nbDecks + nbDecksToAdd > 10) {
            throw new IllegalArgumentException("Too many decks");
        }
        nbDecks += nbDecksToAdd;
        final List<Card> new_deck = Lists.newArrayListWithCapacity(DECK_SIZE * nbDecksToAdd);
        for (int i = 0; i < nbDecksToAdd; ++i) {
            new_deck.addAll(Card.newDeck());
        }
        if (shuffled) {
            shuffle(new_deck);
        }
        deck.addAll(new_deck);

    }

    @Override
    public Collection<Card> getRemainingCards() {
        return Collections.unmodifiableCollection(deck);
    }

    @Override
    public void shuffle() {
        shuffle(deck);
    }

    @Override
    public int nbRemainingCards() {
        return deck.size();
    }

    @Override
    public Collection<Card> dealCards(int nbCardsToDeal) {
        if (nbCardsToDeal <0) {
            throw new IllegalArgumentException("Invalid number of cards to deal: " + nbCardsToDeal);
        }
        if (nbCardsToDeal > deck.size()) {
            return ImmutableList.of();
        } else {
            final Collection<Card> result = Lists.newArrayListWithCapacity(nbCardsToDeal);
            int idx = deck.size();
            for (int i = 0; i < nbCardsToDeal; ++i) {
                result.add(deck.remove(--idx));
            }
            return result;
        }
    }

    private static void shuffle(List<Card> deck) {
        if (!(deck instanceof ArrayList<?>)) {
            throw new IllegalStateException("Passing a non ArrayList to shuffle");
        }

        final Random r = new SecureRandom();
        final int n = deck.size();
        int remaining = n;
        for (int i = 0; i < n; ++i) {
            final int pos = i + r.nextInt(remaining--);
            final Card tmp = deck.get(i);
            deck.set(i, deck.get(pos));
            deck.set(pos, tmp);
        }
    }
}
