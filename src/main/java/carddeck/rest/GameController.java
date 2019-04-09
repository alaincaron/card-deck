package carddeck.rest;

import carddeck.model.Card;
import carddeck.model.Game;
import carddeck.model.Player;
import carddeck.model.Score;
import carddeck.model.Suit;
import carddeck.services.GameService;
import com.google.common.collect.ImmutableMap;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/games")
public class GameController {

    private final GameService gameService;

    public GameController(GameService gameService) {
        this.gameService = gameService;
    }

    @PostMapping
    Game createGame() {
        return gameService.createGame();
    }

    @GetMapping
    Collection<Game> listGames() {
        return gameService.listGames();
    }

    @GetMapping("/{id}")
    Game fetchGame(@PathVariable String id) {
        return gameService
            .fetchGame(id)
            .orElseThrow(notFound(id));
    }

    @DeleteMapping("/{id}")
    void deleteGame(@PathVariable String id) {
         gameService
             .deleteGame(id)
            .orElseThrow(notFound(id));
    }

    @PutMapping("/{id}/addDeck")
    Game addDeck(
        @PathVariable String id,
        @RequestParam(defaultValue = "1") int nbDecks)
    {
        // just ensure we are given a valid number of decks to add.
        // 3 is an upper limit to avoid DoS simple attacks
        if (nbDecks < 1 || nbDecks > 5) {
            throw new IllegalArgumentException("Invalid value for nbDecks");
        }

        return gameService
            .fetchGame(id)
            .map(g -> gameService.addDecks(g, nbDecks))
            .orElseThrow(notFound(id));
    }

    @PostMapping("/{id}/players")
    Player addPlayer(@PathVariable String id) {
      return gameService
          .fetchGame(id)
          .map(gameService::addPlayer)
          .orElseThrow(notFound(id));
    }

    @PutMapping("/{id}/deal/{playerId}")
    Player dealToPlayer(
        @PathVariable("id") String id,
        @PathVariable("playerId") String playerId,
        @RequestParam(value = "nbCards", defaultValue = "1") int nbCards)  {
        if (nbCards <= 0) {
            throw new IllegalArgumentException("Invalid value for nbCards");
        }
        return gameService
            .fetchGame(id)
            .map(g -> gameService.dealCards(g, playerId, nbCards))
            .orElseThrow(notFound(id));
    }

    @DeleteMapping("{id}/player/{playerId}")
    void removePlayer(
        @PathVariable("id") String id,
        @PathVariable("playerId") String playerId) {
        throw new IllegalArgumentException();
    }

    @PutMapping("/{id}/shuffle")
    void shuffle(@PathVariable String id) {
        final Game game = gameService.fetchGame(id).orElseThrow(notFound(id));
        gameService.shuffle(game);
    }

    @GetMapping("/{id}/scores")
    List<Score> getScores(@PathVariable String id) {
        return gameService
            .fetchGame(id)
            .map(gameService::getScores)
            .orElseThrow(notFound(id));
    }

    @GetMapping("/{id}/count_suits")
    Map<Suit, Integer> getCountBySuit(@PathVariable String id) {
        return gameService
            .fetchGame(id)
            .map(gameService::getRemainingBySuit)
            .orElseThrow(notFound(id));
    }

    @GetMapping("/{id}/count_cards")
    List<Map<String, Object>> getCountByCard(@PathVariable String id) {
        return gameService
            .fetchGame(id)
            .map(gameService::getRemainingCards)
            .map(this::toList)
            .orElseThrow(notFound(id));
    }

    private List<Map<String, Object>> toList(Map<Card, Integer> map) {
        return map.entrySet()
                  .stream()
                  .map(e -> ImmutableMap.of("card", e.getKey(), "count", e.getValue()))
            .collect(Collectors.toList());
    }

    private static Supplier<EntityNotFoundException> notFound(String id) {
        return () -> new EntityNotFoundException("Game", id);
    }
}
