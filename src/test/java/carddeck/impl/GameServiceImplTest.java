package carddeck.impl;

import carddeck.dao.GameDAO;
import carddeck.model.Game;
import carddeck.model.Player;
import carddeck.model.Score;
import carddeck.model.Suit;
import carddeck.services.PlayerService;
import com.google.common.collect.Maps;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;

public class GameServiceImplTest {

    private final GameDAO gameDAO = mock(GameDAO.class);
    private final PlayerService playerService = mock(PlayerService.class);
    private final GameServiceImpl impl = new GameServiceImpl(gameDAO, playerService);


    @Test
    public void testScores() {

        final Player p1 = new PlayerImpl("p1");
        final Player p2 = new PlayerImpl("p2");
        final Player p3 = new PlayerImpl("p3");
        final Score score1 = new Score("p1", 12);
        final Score score2 = new Score("p2", 23);
        final Score score3 = new Score("p3", 15);
        final Game game = new GameImpl("game-id");

        game.addPlayer(p1);
        game.addPlayer(p2);
        game.addPlayer(p3);

        doReturn(score1).when(playerService).computeScore(p1);
        doReturn(score2).when(playerService).computeScore(p2);
        doReturn(score3).when(playerService).computeScore(p3);

        List<Score> scores = impl.getScores(game);
        assertEquals(scores, Arrays.asList(score2, score3, score1));
    }

    @Test
    public void getRemainingBySuit() {
        final GameImpl game = new GameImpl("game-id");
        impl.addDecks(game, 3);
        assertEquals(3, game.getNbDecks());
        assertEquals(156, game.getNbRemaining());
        final Map<Suit, Integer> result = impl.getRemainingBySuit(game);
        final Map<Suit, Integer> expected = Maps.newEnumMap(Suit.class);
        for (Suit suit : Suit.values()) {
            expected.put(suit, 39);
        }
        assertEquals(expected, result);
    }
}
