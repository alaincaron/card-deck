package carddeck.impl;

import carddeck.model.Game;
import carddeck.model.GameDeck;
import carddeck.model.Player;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.collect.Maps;

import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

class GameImpl implements Game, Serializable {
    private final String id;

    @JsonIgnore
    private final Map<String, Player> playerMap = Maps.newConcurrentMap();

    @JsonIgnore
    private final GameDeck gameDeck = new GameDeckImpl();

    GameImpl(String id) {
        this.id = id;
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public Map<String, Player> getPlayerMap() {
        return playerMap;
    }

    @Override
    public void addPlayer(Player player) {
        playerMap.put(player.getId(), player);
    }

    @Override
    public Player getPlayer(String id) {
        return playerMap.get(id);
    }

    @Override
    public Player removePlayer(String id) {
        return playerMap.remove(id);
    }

    @Override
    public GameDeck getGameDeck() {
        return gameDeck;
    }

    @Override
    public int getNbDecks() {
        return gameDeck.getNbDecks();
    }

    @JsonProperty
    public List<String> getPlayers() {
        return playerMap.values().stream().map(Player::getId).collect(Collectors.toList());
    }

    @JsonProperty
    int getNbRemaining() {
        return gameDeck.nbRemainingCards();
    }

}
