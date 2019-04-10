package carddeck.model;

import carddeck.dao.WithId;

import java.util.Map;

public interface Game extends WithId {
    @Override
    String getId();

    Map<String, Player> getPlayerMap();
    void addPlayer(Player player);
    Player removePlayer(String id);
    Player getPlayer(String id);

    GameDeck getGameDeck();
}
