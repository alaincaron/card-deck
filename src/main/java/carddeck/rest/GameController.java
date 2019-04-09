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
import java.util.stream.Collectors;

@RestController
@RequestMapping("/games")
public class GameController {

    private final GameService gameService;

    public GameController(GameService gameService) {
        this.gameService = gameService;
    }

    @PostMapping
    public Game createGame() {
        return gameService.createGame();
    }

    @GetMapping
    public Collection<Game> listGames() {
        return gameService.listGames();
    }

    @GetMapping("/{id}")
    public Game fetchGame(@PathVariable String id) {
        return gameService
            .fetchGame(id)
            .orElseThrow(EntityNotFoundExceptionSuppliers.game(id));
    }

    @DeleteMapping("/{id}")
    public void deleteGame(@PathVariable String id) {
        gameService
             .deleteGame(id)
            .orElseThrow(EntityNotFoundExceptionSuppliers.game(id));
    }

    @PutMapping("/{id}/addDeck")
    public Game addDeck(
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
            .orElseThrow(EntityNotFoundExceptionSuppliers.game(id));
    }

    // Players related operations

    @GetMapping("/{id}/players")
    public Collection<String> getPlayers(@PathVariable String id) {
        return gameService
                .fetchGame(id)
                .map(g -> g.getPlayerMap().keySet())
                .orElseThrow(EntityNotFoundExceptionSuppliers.game(id));
    }

    @PostMapping("/{id}/players")
    public Player addPlayer(@PathVariable String id) {
        return gameService
          .fetchGame(id)
          .map(gameService::addPlayer)
          .orElseThrow(EntityNotFoundExceptionSuppliers.game(id));
    }

    @GetMapping("{id}/players/{playerId}")
    public Player showPlayer(
            @PathVariable("id") String id,
            @PathVariable("playerId") String playerId) {

        return gameService
                .fetchGame(id)
                .map(g -> gameService
                        .fetchPlayer(g, playerId)
                        .orElseThrow(EntityNotFoundExceptionSuppliers.player(playerId))
                )
                .orElseThrow(EntityNotFoundExceptionSuppliers.game(id));
    }


    @DeleteMapping("{id}/players/{playerId}")
    public void removePlayer(
        @PathVariable("id") String id,
        @PathVariable("playerId") String playerId) {
        gameService
                .fetchGame(id)
                .map(g -> gameService
                        .deletePlayer(g, playerId)
                        .orElseThrow(EntityNotFoundExceptionSuppliers.player(playerId))
                )
                .orElseThrow(EntityNotFoundExceptionSuppliers.game(id));
    }

    @PutMapping("/{id}/players/{playerId}/deal")
    public Player dealToPlayer(
            @PathVariable("id") String id,
            @PathVariable("playerId") String playerId,
            @RequestParam(value = "nbCards", defaultValue = "1") int nbCards)  {
        if (nbCards <= 0) {
            throw new IllegalArgumentException("Invalid value for nbCards");
        }
        return gameService
                .fetchGame(id)
                .map(g -> gameService.dealCards(g, playerId, nbCards))
                .orElseThrow(EntityNotFoundExceptionSuppliers.game(id));
    }

    // Misc games operations

    @PutMapping("/{id}/shuffle")
    public void shuffle(@PathVariable String id) {
        final Game game = gameService.fetchGame(id).orElseThrow(EntityNotFoundExceptionSuppliers.game(id));
        gameService.shuffle(game);
    }

    @GetMapping("/{id}/scores")
    public List<Score> getScores(@PathVariable String id) {
        return gameService
            .fetchGame(id)
            .map(gameService::getScores)
            .orElseThrow(EntityNotFoundExceptionSuppliers.game(id));
    }

    @GetMapping("/{id}/count_suits")
    public Map<Suit, Integer> getCountBySuit(@PathVariable String id) {
        return gameService
            .fetchGame(id)
            .map(gameService::getRemainingBySuit)
            .orElseThrow(EntityNotFoundExceptionSuppliers.game(id));
    }

    @GetMapping("/{id}/count_cards")
    public List<Map<String, Object>> getCountByCard(@PathVariable String id) {
        return gameService
            .fetchGame(id)
            .map(gameService::getRemainingCards)
            .map(this::toList)
            .orElseThrow(EntityNotFoundExceptionSuppliers.game(id));
    }

    // Helper functions
    private List<Map<String, Object>> toList(Map<Card, Integer> map) {
        return map.entrySet()
                  .stream()
                  .map(e -> ImmutableMap.of("card", e.getKey(), "count", e.getValue()))
            .collect(Collectors.toList());
    }

}
