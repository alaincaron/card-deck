package carddeck.impl;

import carddeck.dao.PlayerFactory;
import carddeck.model.Card;
import carddeck.model.Game;
import carddeck.model.GameDeck;
import carddeck.model.Player;
import carddeck.model.Rank;
import carddeck.model.Score;
import carddeck.services.PlayerService;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Collections;
import java.util.Optional;

@Component
class PlayerServiceImpl implements PlayerService {

    private final PlayerFactory playerFactory;

    PlayerServiceImpl(PlayerFactory playerFactory) {
        this.playerFactory = playerFactory;
    }

    @Override
    public Player addPlayer(Game game) {
        final Player player = playerFactory.create();
        game.addPlayer(player);
        return player;
    }

    @Override
    public Optional<Player> fetchPlayer(Game game, String playerId) {
        return Optional.ofNullable(game.getPlayerMap().get(playerId));
    }

    @Override
    public Optional<Player> deletePlayer(Game game, String playerId) {
        return Optional.ofNullable(game.getPlayerMap().remove(playerId));
    }

    @Override
    public Collection<Player> listPlayers(Game game) {
        return Collections.unmodifiableCollection(game.getPlayerMap().values());
    }

    @Override
    public void dealCards(Game game, Player player, int nbCards) {
        final GameDeck gameDeck = game.getGameDeck();
        final Collection<Card> cards = gameDeck.dealCards(nbCards);
        player.getCards().addAll(cards);
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
