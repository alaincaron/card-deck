package carddeck.impl;

import carddeck.dao.PlayerFactory;
import carddeck.model.Card;
import carddeck.model.GameDeck;
import carddeck.model.Player;
import carddeck.model.Rank;
import carddeck.model.Score;
import carddeck.model.Suit;
import carddeck.services.PlayerService;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class PlayerServiceImplTest {

    private final PlayerFactory playerFactory = new PlayerFactoryImpl();
    private final PlayerService playerService = new PlayerServiceImpl(playerFactory);

    @Test
    public void testScoreEmpty() {
        final Player p = playerFactory.create();
        final Score score = playerService.computeScore(p);
        assertEquals(p.getId(), score.getPlayerId());
        assertEquals(0, score.getScore());
    }

    @Test
    public void testScore()
    {
        final Player p = playerFactory.create();
        p.getCards().add(Card.getCard(Rank.ACE, Suit.DIAMONDS));
        p.getCards().add(Card.getCard(Rank.KING, Suit.SPADES));
        p.getCards().add(Card.getCard(Rank.QUEEN, Suit.CLUBS));
        final Score score = playerService.computeScore(p);
        assertEquals(p.getId(), score.getPlayerId());
        assertEquals(26, score.getScore());
    }

    @Test
    public void dealCards() {
        final GameImpl game = new GameImpl("id");
        final GameDeck gameDeck = game.getGameDeck();
        gameDeck.addDecks(2, false);
        final Player player = new PlayerImpl("id");

        assertEquals(104, game.getNbRemaining());

        // asking for more cards than what is available should not change the state
        playerService.dealCards(game, player, 105);
        assertEquals(0, player.getCards().size());
        assertEquals(104, game.getNbRemaining());

        // ask for one card and see if this is the right one... since the deck hasn't been shuffled
        playerService.dealCards(game, player, 1);

        assertEquals(1, player.getCards().size());
        assertEquals(Card.getCard(Rank.KING, Suit.DIAMONDS), player.getCards().iterator().next());
        assertEquals(103, game.getNbRemaining());

        playerService.dealCards(game, player, 104);

        assertEquals(1, player.getCards().size());
        assertEquals(103, game.getNbRemaining());

        playerService.dealCards(game, player, 103);
        assertEquals(104, player.getCards().size());
        assertEquals(0, game.getNbRemaining());
    }

}
