package carddeck.model;

import carddeck.dao.WithId;

import java.util.Collection;

public interface Player extends WithId {
    Collection<Card> getCards();
}
