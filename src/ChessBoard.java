public class ChessBoard {

    private final ChessPiece[][] board;

    public ChessBoard() {
        board = new ChessPiece[8][8];

        /* Adding Chess Pieces */
        board[0][0] = new Rook(false);
        board[0][7] = new Rook(false);
        board[7][0] = new Rook(true);
        board[7][7] = new Rook(true);

        board[0][1] = new Knight(false);
        board[0][6] = new Knight(false);
        board[7][1] = new Knight(true);
        board[7][6] = new Knight(true);

        board[0][2] = new Bishop(false);
        board[0][5] = new Bishop(false);
        board[7][2] = new Bishop(true);
        board[7][5] = new Bishop(true);

        board[0][3] = new Queen(false);
        board[7][3] = new Queen(true);
        board[0][4] = new King(false);
        board[7][4] = new King(true);

        for(int i = 0; i < 8; i++) {
            board[1][i] = new Pawn(false);
        }
        for(int i = 0; i < 8; i++) {
            board[6][i] = new Pawn(true);
        }
    }

    public ChessPiece[][] getBoard() {
        return board;
    }

    public void movePiece(int y, int x, int i, int j) {
        board[y][x] = board[i][j];
        board[i][j] = null;
    }
}
