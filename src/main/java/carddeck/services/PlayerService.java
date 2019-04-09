package carddeck.services;

import carddeck.model.Player;
import carddeck.model.Score;

import java.util.Optional;

public interface PlayerService {
    Player createPlayer();
    Optional<Player> fetch(String playerId);
    Score computeScore(Player player);
}
