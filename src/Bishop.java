import java.util.ArrayList;

public class Bishop implements ChessPiece {

    private final Piece piece;
    private final Team team;
    private ThreatLevel threatLevel;
    private boolean firstMove;
    private ArrayList<int[]> range;

    public Bishop(boolean teamOne) {
        piece = Piece.BISHOP;
        team = teamOne? Team.WHITE : Team.BLACK;
        threatLevel = ThreatLevel.FREE;
        firstMove = true;
        range = new ArrayList<>();
    }
    public Piece getPiece() {
        return piece;
    }

    public Team getTeam() {
        return team;
    }

    public ThreatLevel getThreatLevel() {
        return threatLevel;
    }

    public void setThreatLevel(ThreatLevel level) {
        threatLevel = level;
    }

    public boolean isFirstMove() {
        return firstMove;
    }

    public void setFirstMove(boolean firstMove) {
        this.firstMove = firstMove;
    }

    public ArrayList<int[]> getRange() {
        return range;
    }

    public void setRange(ArrayList<int[]> range) {
        this.range = range;
    }
}
