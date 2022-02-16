import java.util.ArrayList;
import java.util.List;

public class ChessModel {

    public enum Status {BLACK_WON, WHITE_WON, DRAW, UNDECIDED}

    private ChessBoard board;
    private ChessPiece.Team turn;
    private Status status;
    private List<ChessView> views;
    private ArrayList<int[]> selRange;
    private int[] selCoordinate;

    public ChessModel() {
        board = new ChessBoard();
        turn = ChessPiece.Team.WHITE;
        status = Status.UNDECIDED;
        views = new ArrayList<>();
        selRange = new ArrayList<>();
        selCoordinate = new int[2];
    }

    public void addView(ChessView chessView) {
        views.add(chessView);
    }

    public ChessBoard getBoard() {
        return board;
    }

    public ChessPiece.Team getTurn() {
        return turn;
    }

    public void changeTurn() {
        if(turn == ChessPiece.Team.WHITE)
            turn = ChessPiece.Team.BLACK;
        else
            turn = ChessPiece.Team.WHITE;
    }

    public ArrayList<int[]> getSelRange() {
        return selRange;
    }

    public void setSelRange(ArrayList<int[]> selRange) {
        this.selRange = selRange;
    }

    public int[] getSelCoordinate() {
        return selCoordinate;
    }

    public void setSelCoordinate(int[] selCoordinate) {
        this.selCoordinate = selCoordinate;
    }

    public void move(int y, int x) {
        board.movePiece(y, x, selCoordinate[0], selCoordinate[1]);
        for(ChessView view : views) {
            view.handleChessUpdate(this, status, y, x, selCoordinate[0], selCoordinate[1]);
        }
    }
}
