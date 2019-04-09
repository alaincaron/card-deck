package carddeck.impl;

import carddeck.model.Card;
import carddeck.model.Player;
import com.google.common.collect.Lists;

import java.util.Collection;

class PlayerImpl implements Player {

    private final String id;
    private final Collection<Card> cards = Lists.newLinkedList();

    PlayerImpl(String id) {
        this.id = id;
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public Collection<Card> getCards() {
        return cards;
    }
}
