package carddeck.model;

import com.google.common.base.MoreObjects;

public class Score {
    private final String playerId;
    private final int score;

    public Score(String playerId, int score) {
        this.playerId = playerId;
        this.score = score;
    }

    public String getPlayerId() {
        return playerId;
    }

    public int getScore() {
        return score;
    }


    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                          .add("playerId", playerId)
                          .add("score", score)
                          .toString();
    }
}
