package carddeck.impl;

import carddeck.dao.GameDeckDAO;
import carddeck.model.GameDeck;
import org.springframework.stereotype.Component;

@Component
class InMemoryGameDeckDAOImpl extends AbstractInMemoryDAO<GameDeck> implements GameDeckDAO {
    InMemoryGameDeckDAOImpl() {
        super(GameDeckImpl::new);
    }
}
