import javax.swing.*;
import java.awt.*;

public class ChessFrame extends JFrame implements ChessView {

    private JButton[][] boardFrame;
    private ChessModel chessModel;
    private ChessController chessController;

    public ChessFrame() {
        super("Chess");
        this.setLayout(new GridLayout(8, 8));
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(600, 600);

        boardFrame = new JButton[8][8];
        chessModel = new ChessModel();
        chessController = new ChessController(chessModel, this);

        chessModel.addView(this);

        boolean buttonColor = false;
        for(int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                JButton button = new JButton();
                if(buttonColor) {
                    button.setBackground(new Color(255, 255,255));
                } else {
                    button.setBackground(new Color(50, 50, 50));
                }
                if(j != 7)
                    buttonColor = !buttonColor;
                button.setActionCommand(i + " " + j);
                boardFrame[i][j] = button;

                if(chessModel.getBoard().getBoard()[i][j] != null)
                    boardFrame[i][j].setIcon(new ImageIcon("img/" + chessModel.getBoard().getBoard()[i][j].getTeam().toString().toLowerCase() + "_" +
                            chessModel.getBoard().getBoard()[i][j].getPiece().toString().toLowerCase() + ".png"));
                else
                    boardFrame[i][j].setIcon(null);

                button.addActionListener(chessController);
                this.add(button);
            }
        }
        this.setVisible(true);
    }

    public JButton[][] getBoardFrame() {
        return boardFrame;
    }

    public void handleChessUpdate(ChessModel chessModel, ChessModel.Status status, int y, int x, int i, int j) {
        if(chessModel.getBoard().getBoard()[y][x] != null)
            boardFrame[y][x].setIcon(new ImageIcon("img/" + chessModel.getBoard().getBoard()[y][x].getTeam().toString().toLowerCase() + "_" +
                    chessModel.getBoard().getBoard()[y][x].getPiece().toString().toLowerCase() + ".png"));
        else
            boardFrame[y][x].setIcon(null);
        if(chessModel.getBoard().getBoard()[i][j] != null)
            boardFrame[i][j].setIcon(new ImageIcon("img/" + chessModel.getBoard().getBoard()[i][j].getTeam().toString().toLowerCase() + "_" +
                    chessModel.getBoard().getBoard()[i][j].getPiece().toString().toLowerCase() + ".png"));
        else
            boardFrame[i][j].setIcon(null);
    }

    public static void main(String[] args) {
        new ChessFrame();
    }
}
