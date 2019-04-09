package carddeck.rest;

import carddeck.model.Player;
import carddeck.services.PlayerService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.function.Supplier;

@RestController
@RequestMapping("/players")


class PlayerController {

    private final PlayerService playerService;

    PlayerController(PlayerService playerService) {
        this.playerService = playerService;
    }

    @GetMapping("/{id}")
    Player fetchPlayer(@PathVariable String id) {
        return playerService
            .fetch(id)
            .orElseThrow(notFound(id));
    }

    private static Supplier<EntityNotFoundException> notFound(String id) {
        return () -> new EntityNotFoundException("Player", id);
    }

}
