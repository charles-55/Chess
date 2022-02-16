import java.util.ArrayList;

public interface ChessPiece {

    enum Piece {PAWN, ROOK, KNIGHT, BISHOP, KING, QUEEN}
    enum Team {BLACK, WHITE}
    enum ThreatLevel {FREE, DANGER, CAPTURED}

    Piece getPiece();
    Team getTeam();
    ThreatLevel getThreatLevel();
    void setThreatLevel(ThreatLevel level);
    boolean isFirstMove();
    void setFirstMove(boolean firstMove);
    ArrayList<int[]> getRange();
    void setRange(ArrayList<int[]> range);
}
