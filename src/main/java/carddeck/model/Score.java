package carddeck.model;

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
}
