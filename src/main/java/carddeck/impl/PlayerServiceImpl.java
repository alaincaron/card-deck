package carddeck.impl;

import carddeck.dao.PlayerDAO;
import carddeck.model.Card;
import carddeck.model.Player;
import carddeck.model.Rank;
import carddeck.model.Score;
import carddeck.services.PlayerService;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Optional;

@Component
public class PlayerServiceImpl implements PlayerService {
    private final PlayerDAO playerDAO;

    public PlayerServiceImpl(PlayerDAO playerDAO) {
        this.playerDAO = playerDAO;
    }

    @Override
    public Player createPlayer() {
        return playerDAO.create();
    }

    @Override
    public Optional<Player> fetch(String playerId) {
        return playerDAO.fetch(playerId);
    }

    @Override
    public Score computeScore(Player player) {
        final Collection<Card> cards = player.getCards();
        final int score;
        if (cards == null) {
            score = 0;
        } else {
            score = cards.stream().map(Card::getRank).mapToInt(Rank::getScore).sum();
        }
        return new Score(player.getId(), score);
    }
}
