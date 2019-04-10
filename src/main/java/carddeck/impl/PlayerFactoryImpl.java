package carddeck.impl;

import carddeck.dao.PlayerFactory;
import carddeck.model.Player;
import org.springframework.stereotype.Component;

@Component
class PlayerFactoryImpl extends AbstractFactory<Player> implements PlayerFactory {
    PlayerFactoryImpl() {
        super(PlayerImpl::new);
    }
}
