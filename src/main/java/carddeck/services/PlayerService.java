package carddeck.services;

import carddeck.model.Game;
import carddeck.model.Player;
import carddeck.model.Score;
import carddeck.rest.EntityNotFoundExceptionSuppliers;

import java.util.Collection;
import java.util.Optional;

public interface PlayerService {

    Player addPlayer(Game game);

    Optional<Player> fetchPlayer(Game game, String playerId);
    default Player fetchPlayerOrFail(Game game, String playerId) {
        return fetchPlayer(game, playerId).orElseThrow(EntityNotFoundExceptionSuppliers.player(playerId));
    }

    Optional<Player> deletePlayer(Game game, String playerId);
    default Player deletePlayerOrFail(Game game, String playerId) {
        return deletePlayer(game, playerId).orElseThrow(EntityNotFoundExceptionSuppliers.player(playerId));
    }

    Collection<Player> listPlayers(Game game);

    void dealCards(Game game, Player player, int nbCards);
    Score computeScore(Player player);

}
