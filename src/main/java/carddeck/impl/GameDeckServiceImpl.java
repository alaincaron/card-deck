package carddeck.impl;

import carddeck.dao.GameDeckDAO;
import carddeck.model.GameDeck;
import carddeck.services.GameDeckService;
import org.springframework.stereotype.Component;

@Component
class GameDeckServiceImpl implements GameDeckService {
    private final GameDeckDAO gameDeckDAO;

    GameDeckServiceImpl(GameDeckDAO gameDeckDAO) {
        this.gameDeckDAO = gameDeckDAO;

    }
    @Override
    public GameDeck createGameDeck() {
        return gameDeckDAO.create();
    }
}
