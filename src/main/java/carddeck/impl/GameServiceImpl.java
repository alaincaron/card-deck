package carddeck.impl;

import carddeck.dao.GameDAO;
import carddeck.model.Card;
import carddeck.model.Game;
import carddeck.model.GameDeck;
import carddeck.model.Player;
import carddeck.model.Score;
import carddeck.model.Suit;
import carddeck.rest.EntityNotFoundException;
import carddeck.rest.EntityNotFoundExceptionSuppliers;
import carddeck.services.GameDeckService;
import carddeck.services.GameService;
import carddeck.services.PlayerService;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.TreeMap;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
class GameServiceImpl implements GameService {

    private final GameDAO gameDAO;
    private final GameDeckService gameDeckService;
    private final PlayerService playerService;

    public GameServiceImpl(GameDAO gameDAO, GameDeckService gameDeckService, PlayerService playerService) {
        this.gameDAO = gameDAO;
        this.gameDeckService = gameDeckService;
        this.playerService = playerService;
    }

    @Override
    public Game createGame() {
        final Game game = gameDAO.create();
        getGameDeck(game);
        return game;
    }

    @Override
    public Optional<Game> fetchGame(String id) {
        return gameDAO.fetch(id);
    }

    @Override
    public Game saveGame(Game game) {
        return gameDAO.save(game);
    }

    @Override
    public Collection<Game> listGames() {
        return gameDAO.list();
    }

    @Override
    public Optional<Game> deleteGame(String id) {
        return gameDAO.delete(id);
    }

    @Override
    public Game addDecks(Game game, int nbDecks) {
        final GameDeck gameDeck = getGameDeck(game);
        gameDeck.addDecks(nbDecks, false);
        return game;
    }

    @Override
    public Player addPlayer(Game game) {
       final Player player = playerService.createPlayer();
       game.addPlayer(player);
       return player;
    }

    @Override
    public Optional<Player> deletePlayer(Game g, String playerId) {
        return Optional.ofNullable(g.getPlayerMap().remove(playerId));
    }

    @Override
    public Optional<Player> fetchPlayer(Game g, String playerId) {
        return Optional.ofNullable(g.getPlayerMap().get(playerId));
    }

    @Override
    public Player dealCards(Game game, String playerId, int nbCards) {
        final Player player =
            playerService
                .fetch(playerId)
                .orElseThrow(EntityNotFoundExceptionSuppliers.player(playerId));

        final GameDeck gameDeck = getGameDeck(game);
        final Collection<Card> cards = gameDeck.dealCards(nbCards);
        player.getCards().addAll(cards);

        return player;
    }

    @Override
    public void shuffle(Game game) {
        final GameDeck gameDeck = getGameDeck(game);
        gameDeck.shuffle();
    }

    @Override
    public List<Score> getScores(Game game) {
        final Collection<Player> players = game.getPlayerMap().values();
        return players
            .stream()
            .map(playerService::computeScore)
            .sorted(Comparator.comparingInt(Score::getScore).reversed())
            .collect(Collectors.toList());
    }

    @Override
    public Map<Suit, Integer> getRemainingBySuit(Game game) {
        final GameDeck gameDeck = getGameDeck(game);
        return gameDeck
            .getRemainingCards()
            .stream()
            .collect(
                Collectors.groupingBy(Card::getSuit,
                                      Collectors.reducing(0, e -> 1, Integer::sum)
                )
            );
    }

    @Override
    public Map<Card, Integer> getRemainingCards(Game game) {
        final GameDeck gameDeck = getGameDeck(game);
        return gameDeck
            .getRemainingCards()
            .stream()
            .collect(
                Collectors.groupingBy(Function.identity(),
                                      () -> new TreeMap<>(CARD_COMPARATOR),
                                      Collectors.reducing(0, e -> 1, Integer::sum)
                )
            );

    }

    private static final Comparator<Card> CARD_COMPARATOR = (card1, card2) -> {

        final int c = card1.getSuit().compareTo(card2.getSuit());
        if (c != 0) {
            return c;
        }
        return card2.getRank().compareTo(card1.getRank());
    };

    private GameDeck getGameDeck(Game game) {
        GameDeck gameDeck = game.getGameDeck();

        if (gameDeck == null) {
            gameDeck = gameDeckService.createGameDeck();
            game.setGameDeck(gameDeck);
        }
        return gameDeck;
    }

}
