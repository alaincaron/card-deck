package carddeck.services;

import carddeck.model.Card;
import carddeck.model.Game;
import carddeck.model.Score;
import carddeck.model.Suit;
import carddeck.rest.EntityNotFoundExceptionSuppliers;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface GameService {

    Game createGame();
    Optional<Game> fetchGame(String id);

    default Game fetchGameOrFail(String id) {
        return fetchGame(id).orElseThrow(EntityNotFoundExceptionSuppliers.game(id));
    }

    Game saveGame(Game game);
    Collection<Game> listGames();
    Optional<Game> deleteGame(String id);

    void addDecks(Game Game, int nbDecks);

    void shuffle(Game game);
    List<Score> getScores(Game game);

    Map<Suit, Integer> getRemainingBySuit(Game game);
    Map<Card, Integer> getRemainingCards(Game game);
}

