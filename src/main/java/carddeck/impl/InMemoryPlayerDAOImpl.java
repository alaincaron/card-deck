package carddeck.impl;

import carddeck.dao.PlayerDAO;
import carddeck.model.Player;
import org.springframework.stereotype.Component;

@Component
class InMemoryPlayerDAOImpl extends AbstractInMemoryDAO<Player> implements PlayerDAO {

    InMemoryPlayerDAOImpl() {
        super(PlayerImpl::new);
    }
}
