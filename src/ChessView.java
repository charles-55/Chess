public interface ChessView {

    void handleChessUpdate(ChessModel chessModel, ChessModel.Status status, int y, int x, int i, int j);
}
