package carddeck.impl;

import carddeck.dao.GameDAO;
import carddeck.model.Game;
import org.springframework.stereotype.Component;

@Component
class InMemoryGameDAOImpl extends AbstractInMemoryDAO<Game> implements GameDAO {

    InMemoryGameDAOImpl() {
        super(GameImpl::new);
    }
}
