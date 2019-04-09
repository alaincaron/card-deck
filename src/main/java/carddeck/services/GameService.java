package carddeck.services;

import carddeck.model.Card;
import carddeck.model.Game;
import carddeck.model.Player;
import carddeck.model.Score;
import carddeck.model.Suit;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface GameService {

    public Game createGame();
    public Optional<Game> fetchGame(String id);
    public Game saveGame(Game game);
    public Collection<Game> listGames();
    public Optional<Game> deleteGame(String id);

    public Game addDecks(Game Game, int nbDecks);
    public Player addPlayer(Game game);

    public void shuffle(Game game);
    Player dealCards(Game game, String playerId, int nbCards);
    List<Score> getScores(Game game);

    Map<Suit, Integer> getRemainingBySuit(Game game);
    Map<Card, Integer> getRemainingCards(Game game);

    Optional<Player> deletePlayer(Game g, String playerId);

    Optional<Player> fetchPlayer(Game g, String playerId);
}

