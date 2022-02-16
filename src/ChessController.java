import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class ChessController implements ActionListener {

    ChessModel model;
    ChessFrame frame;

    ArrayList<int[]> freeRange;
    ArrayList<int[]> attackRange;

    public ChessController(ChessModel model, ChessFrame frame) {
        this.model = model;
        this.frame = frame;

        freeRange = new ArrayList<>();
        attackRange = new ArrayList<>();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        boolean buttonColor = false;
        for(int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (buttonColor) {
                    frame.getBoardFrame()[i][j].setBackground(new Color(255, 255, 255));
                } else {
                    frame.getBoardFrame()[i][j].setBackground(new Color(50, 50, 50));
                }
                if (j != 7)
                    buttonColor = !buttonColor;
            }
        }

        String[] coordinates = e.getActionCommand().split(" ");
        int y = Integer.parseInt(coordinates[0]);
        int x = Integer.parseInt(coordinates[1]);

        if(model.getBoard().getBoard()[y][x] != null) {
            if(model.getBoard().getBoard()[y][x].getTeam() == model.getTurn()) {
                ChessPiece.Piece piece = model.getBoard().getBoard()[y][x].getPiece();
                freeRange = getFreeRange(y, x, piece);
                attackRange = getAttackRange(y, x, piece);
                highLightFreeRange(freeRange);
                highlightAttackRange(attackRange);
            }
        }

        boolean moved = false;
        for(int[] coordinate : model.getSelRange()) {
            if((coordinate[0] == y) && (coordinate[1] == x)) {
                model.move(y, x);
                model.setSelRange(new ArrayList<>());
                if(model.getBoard().getBoard()[y][x].getPiece() == ChessPiece.Piece.PAWN)
                    model.getBoard().getBoard()[y][x].setFirstMove(false);
                model.changeTurn();
                moved = true;
            }
        }
        if(!moved) {
            model.setSelRange(getTotalRange());
            model.setSelCoordinate(new int[]{y, x});
        }
    }

    private ArrayList<int[]> getFreeRange(int y, int x, ChessPiece.Piece piece) {
        ArrayList<int[]> range = new ArrayList<>();
        int p, q;

        switch (piece) {
            case PAWN -> {
                if (model.getBoard().getBoard()[y][x].isFirstMove()) {
                    if ((model.getBoard().getBoard()[y][x].getTeam() == ChessPiece.Team.BLACK) && (model.getBoard().getBoard()[y + 2][x] == null))
                        range.add(new int[]{y + 2, x});
                    else if ((model.getBoard().getBoard()[y][x].getTeam() == ChessPiece.Team.WHITE) && (model.getBoard().getBoard()[y - 2][x] == null))
                        range.add(new int[]{y - 2, x});
                }
                if ((model.getBoard().getBoard()[y][x].getTeam() == ChessPiece.Team.BLACK) && (model.getBoard().getBoard()[y + 1][x] == null))
                    range.add(new int[]{y + 1, x});
                else if ((model.getBoard().getBoard()[y][x].getTeam() == ChessPiece.Team.WHITE) && (model.getBoard().getBoard()[y - 1][x] == null))
                    range.add(new int[]{y - 1, x});
            }
            case ROOK -> {
                p = y - 1;
                while ((p > -1) && (model.getBoard().getBoard()[p][x] == null)) {
                    range.add(new int[]{p, x});
                    p--;
                }
                p = y + 1;
                while ((p < 8) && (model.getBoard().getBoard()[p][x] == null)) {
                    range.add(new int[]{p, x});
                    p++;
                }

                p = x - 1;
                while ((p > -1) && (model.getBoard().getBoard()[y][p] == null)) {
                    range.add(new int[]{y, p});
                    p--;
                }
                p = x + 1;
                while ((p < 8) && (model.getBoard().getBoard()[y][p] == null)) {
                    range.add(new int[]{y, p});
                    p++;
                }
            }
            case KNIGHT -> {
                if ((x+2 < 8) && (y+1 < 8) && (model.getBoard().getBoard()[y+1][x+2] == null)) {
                    range.add(new int[]{y+1, x+2});
                }
                if ((x+2 < 8) && (y-1 > -1) && (model.getBoard().getBoard()[y-1][x+2] == null)) {
                    range.add(new int[]{y-1, x+2});
                }
                if ((x-2 > -1) && (y+1 < 8) && (model.getBoard().getBoard()[y+1][x-2] == null)) {
                    range.add(new int[]{y+1, x-2});
                }
                if ((x-2 > -1) && (y-1 > -1) && (model.getBoard().getBoard()[y-1][x-2] == null)) {
                    range.add(new int[]{y-1, x-2});
                }
                if ((x+1 < 8) && (y+2 < 8) && (model.getBoard().getBoard()[y+2][x+1] == null)) {
                    range.add(new int[]{y+2, x+1});
                }
                if ((x+1 < 8) && (y-2 > -1) && (model.getBoard().getBoard()[y-2][x+1] == null)) {
                    range.add(new int[]{y-2, x+1});
                }
                if ((x-1 > -1) && (y+2 < 8) && (model.getBoard().getBoard()[y+2][x-1] == null)) {
                    range.add(new int[]{y+2, x-1});
                }
                if ((x-1 > -1) && (y-2 > -1) && (model.getBoard().getBoard()[y-2][x-1] == null)) {
                    range.add(new int[]{y-2, x-1});
                }
            }
            case BISHOP -> {
                p = y - 1;
                q = x - 1;
                while ((p > -1) && (q > -1) && (model.getBoard().getBoard()[p][q] == null)) {
                    range.add(new int[]{p, q});
                    p--;
                    q--;
                }
                p = y + 1;
                q = x + 1;
                while ((p < 8) && (q < 8) && (model.getBoard().getBoard()[p][q] == null)) {
                    range.add(new int[]{p, q});
                    p++;
                    q++;
                }
                p = y + 1;
                q = x - 1;
                while ((p < 8) && (q > -1) && (model.getBoard().getBoard()[p][q] == null)) {
                    range.add(new int[]{p, q});
                    p++;
                    q--;
                }
                p = y - 1;
                q = x + 1;
                while ((p > -1) && (q < 8) && (model.getBoard().getBoard()[p][q] == null)) {
                    range.add(new int[]{p, q});
                    p--;
                    q++;
                }
            }
            case KING -> {
                for (int i = -1; i < 2; i++) {
                    if (((y + i) > -1) && ((y + i) < 8) && ((x + 1) < 8) && (model.getBoard().getBoard()[y + i][x + 1] == null)) {
                        range.add(new int[]{y + i, x + 1});
                    }
                }
                for (int i = -1; i < 2; i++) {
                    if (((y + i) > -1) && ((y + i) < 8) && ((x - 1) > -1) && (model.getBoard().getBoard()[y + i][x - 1] == null)) {
                        range.add(new int[]{y + i, x - 1});
                    }
                }
                if (((y + 1) < 8) && (model.getBoard().getBoard()[y + 1][x] == null))
                    range.add(new int[]{y + 1, x});
                if (((y - 1) > -1) && (model.getBoard().getBoard()[y - 1][x] == null))
                    range.add(new int[]{y - 1, x});
            }
            case QUEEN -> {
                p = y - 1;
                while ((p > -1) && (model.getBoard().getBoard()[p][x] == null)) {
                    range.add(new int[]{p, x});
                    p--;
                }
                p = y + 1;
                while ((p < 8) && (model.getBoard().getBoard()[p][x] == null)) {
                    range.add(new int[]{p, x});
                    p++;
                }

                p = x - 1;
                while ((p > -1) && (model.getBoard().getBoard()[y][p] == null)) {
                    range.add(new int[]{y, p});
                    p--;
                }
                p = x + 1;
                while ((p < 8) && (model.getBoard().getBoard()[y][p] == null)) {
                    range.add(new int[]{y, p});
                    p++;
                }

                p = y - 1;
                q = x - 1;
                while ((p > -1) && (q > -1) && (model.getBoard().getBoard()[p][q] == null)) {
                    range.add(new int[]{p, q});
                    p--;
                    q--;
                }
                p = y + 1;
                q = x + 1;
                while ((p < 8) && (q < 8) && (model.getBoard().getBoard()[p][q] == null)) {
                    range.add(new int[]{p, q});
                    p++;
                    q++;
                }
                p = y + 1;
                q = x - 1;
                while ((p < 8) && (q > -1) && (model.getBoard().getBoard()[p][q] == null)) {
                    range.add(new int[]{p, q});
                    p++;
                    q--;
                }
                p = y - 1;
                q = x + 1;
                while ((p > -1) && (q < 8) && (model.getBoard().getBoard()[p][q] == null)) {
                    range.add(new int[]{p, q});
                    p--;
                    q++;
                }
            }
        }

        return range;
    }

    private ArrayList<int[]> getAttackRange(int y, int x, ChessPiece.Piece piece) {
        ArrayList<int[]> range = new ArrayList<>();
        int p, q;
        boolean pieceInRange;

        switch(piece) {
            case PAWN -> {
                if ((x+1 < 8) && (y+1 < 8)) {
                    if ((model.getBoard().getBoard()[y][x].getTeam() == ChessPiece.Team.BLACK) && (model.getBoard().getBoard()[y + 1][x + 1] != null)) {
                        if (model.getBoard().getBoard()[y + 1][x + 1].getTeam() == ChessPiece.Team.WHITE)
                            range.add(new int[]{y + 1, x + 1});
                    }
                }
                if ((x-1 > -1) && (y+1 < 8)) {
                    if ((model.getBoard().getBoard()[y][x].getTeam() == ChessPiece.Team.BLACK) && (model.getBoard().getBoard()[y + 1][x - 1] != null)) {
                        if (model.getBoard().getBoard()[y + 1][x - 1].getTeam() == ChessPiece.Team.WHITE)
                            range.add(new int[]{y + 1, x - 1});
                    }
                }
                if ((x+1 < 8) && (y-1 > -1)) {
                    if ((model.getBoard().getBoard()[y][x].getTeam() == ChessPiece.Team.WHITE) && (model.getBoard().getBoard()[y - 1][x + 1] != null)) {
                        if (model.getBoard().getBoard()[y - 1][x + 1].getTeam() == ChessPiece.Team.BLACK)
                            range.add(new int[]{y - 1, x + 1});
                    }
                }
                if ((x-1 > -1) && (y-1 > -1)) {
                    if ((model.getBoard().getBoard()[y][x].getTeam() == ChessPiece.Team.WHITE) && (model.getBoard().getBoard()[y - 1][x - 1] != null)) {
                        if (model.getBoard().getBoard()[y - 1][x - 1].getTeam() == ChessPiece.Team.BLACK)
                            range.add(new int[]{y - 1, x - 1});
                    }
                }
            }
            case ROOK -> {
                pieceInRange = false;
                p = y - 1;
                while ((p > -1) && !pieceInRange) {
                    if (model.getBoard().getBoard()[p][x] != null) {
                        if (model.getBoard().getBoard()[y][x].getTeam() != model.getBoard().getBoard()[p][x].getTeam())
                            range.add(new int[]{p, x});
                        pieceInRange = true;
                    }
                    p--;
                }
                pieceInRange = false;
                p = y + 1;
                while ((p < 8) && !pieceInRange) {
                    if (model.getBoard().getBoard()[p][x] != null) {
                        if (model.getBoard().getBoard()[y][x].getTeam() != model.getBoard().getBoard()[p][x].getTeam())
                            range.add(new int[]{p, x});
                        pieceInRange = true;
                    }
                    p++;
                }

                pieceInRange = false;
                p = x - 1;
                while ((p > -1) && !pieceInRange) {
                    if (model.getBoard().getBoard()[y][p] != null) {
                        if (model.getBoard().getBoard()[y][x].getTeam() != model.getBoard().getBoard()[y][p].getTeam())
                            range.add(new int[]{y, p});
                        pieceInRange = true;
                    }
                    p--;
                }
                pieceInRange = false;
                p = x + 1;
                while ((p < 8) && !pieceInRange) {
                    if (model.getBoard().getBoard()[y][p] != null) {
                        if (model.getBoard().getBoard()[y][x].getTeam() != model.getBoard().getBoard()[y][p].getTeam())
                            range.add(new int[]{y, p});
                        pieceInRange = true;
                    }
                    p++;
                }
            }
            case KNIGHT -> {
                if ((x+2 < 8) && (y+1 < 8) && (model.getBoard().getBoard()[y+1][x+2] != null)) {
                    if (model.getBoard().getBoard()[y][x].getTeam() != model.getBoard().getBoard()[y+1][x+2].getTeam())
                        range.add(new int[]{y+1, x+2});
                }
                if ((x+2 < 8) && (y-1 > -1) && (model.getBoard().getBoard()[y-1][x+2] != null)) {
                    if (model.getBoard().getBoard()[y][x].getTeam() != model.getBoard().getBoard()[y-1][x+2].getTeam())
                        range.add(new int[]{y-1, x+2});
                }
                if ((x-2 > -1) && (y+1 < 8) && (model.getBoard().getBoard()[y+1][x-2] != null)) {
                    if (model.getBoard().getBoard()[y][x].getTeam() != model.getBoard().getBoard()[y+1][x-2].getTeam())
                        range.add(new int[]{y+1, x-2});
                }
                if ((x-2 > -1) && (y-1 > -1) && (model.getBoard().getBoard()[y-1][x-2] != null)) {
                    if (model.getBoard().getBoard()[y][x].getTeam() != model.getBoard().getBoard()[y-1][x-2].getTeam())
                        range.add(new int[]{y-1, x-2});
                }
                if ((x+1 < 8) && (y+2 < 8) && (model.getBoard().getBoard()[y+2][x+1] != null)) {
                    if (model.getBoard().getBoard()[y][x].getTeam() != model.getBoard().getBoard()[y+2][x+1].getTeam())
                        range.add(new int[]{y+2, x+1});
                }
                if ((x+1 < 8) && (y-2 > -1) && (model.getBoard().getBoard()[y-2][x+1] != null)) {
                    if (model.getBoard().getBoard()[y][x].getTeam() != model.getBoard().getBoard()[y-2][x+1].getTeam())
                        range.add(new int[]{y-2, x+1});
                }
                if ((x-1 > -1) && (y+2 < 8) && (model.getBoard().getBoard()[y+2][x-1] != null)) {
                    if (model.getBoard().getBoard()[y][x].getTeam() != model.getBoard().getBoard()[y+2][x-1].getTeam())
                        range.add(new int[]{y+2, x-1});
                }
                if ((x-1 > -1) && (y-2 > -1) && (model.getBoard().getBoard()[y-2][x-1] != null)) {
                    if (model.getBoard().getBoard()[y][x].getTeam() != model.getBoard().getBoard()[y-2][x-1].getTeam())
                        range.add(new int[]{y-2, x-1});
                }
            }
            case BISHOP -> {
                pieceInRange = false;
                p = y - 1;
                q = x - 1;
                while ((p > -1) && (q > -1) && !pieceInRange) {
                    if (model.getBoard().getBoard()[p][q] != null) {
                        if (model.getBoard().getBoard()[y][x].getTeam() != model.getBoard().getBoard()[p][q].getTeam())
                            range.add(new int[]{p, q});
                        pieceInRange = true;
                    }
                    p--;
                    q--;
                }
                pieceInRange = false;
                p = y + 1;
                q = x + 1;
                while ((p < 8) && (q < 8) && !pieceInRange) {
                    if (model.getBoard().getBoard()[p][q] != null) {
                        if (model.getBoard().getBoard()[y][x].getTeam() != model.getBoard().getBoard()[p][q].getTeam())
                            range.add(new int[]{p, q});
                        pieceInRange = true;
                    }
                    p++;
                    q++;
                }
                pieceInRange = false;
                p = y + 1;
                q = x - 1;
                while ((p < 8) && (q > -1) && !pieceInRange) {
                    if (model.getBoard().getBoard()[p][q] != null) {
                        if (model.getBoard().getBoard()[y][x].getTeam() != model.getBoard().getBoard()[p][q].getTeam())
                            range.add(new int[]{p, q});
                        pieceInRange = true;
                    }
                    p++;
                    q--;
                }
                pieceInRange = false;
                p = y - 1;
                q = x + 1;
                while ((p > -1) && (q < 8) && !pieceInRange) {
                    if (model.getBoard().getBoard()[p][q] != null) {
                        if (model.getBoard().getBoard()[y][x].getTeam() != model.getBoard().getBoard()[p][q].getTeam())
                            range.add(new int[]{p, q});
                        pieceInRange = true;
                    }
                    p--;
                    q++;
                }
            }
            case KING -> {
                for (int i = -1; i < 2; i++) {
                    if (((y + i) > -1) && ((y + i) < 8) && ((x + 1) < 8) && (model.getBoard().getBoard()[y + i][x + 1] != null)) {
                        if (model.getBoard().getBoard()[y][x].getTeam() != model.getBoard().getBoard()[y+i][x+1].getTeam())
                            range.add(new int[]{y + i, x + 1});
                    }
                }
                for (int i = -1; i < 2; i++) {
                    if (((y + i) > -1) && ((y + i) < 8) && ((x - 1) > -1) && (model.getBoard().getBoard()[y + i][x - 1] != null)) {
                        if (model.getBoard().getBoard()[y][x].getTeam() != model.getBoard().getBoard()[y+i][x-1].getTeam())
                        range.add(new int[]{y + i, x - 1});
                    }
                }
                if (((y + 1) < 8) && (model.getBoard().getBoard()[y + 1][x] != null)) {
                    if (model.getBoard().getBoard()[y][x].getTeam() != model.getBoard().getBoard()[y+1][x].getTeam())
                        range.add(new int[]{y + 1, x});
                }
                if (((y - 1) > -1) && (model.getBoard().getBoard()[y - 1][x] != null)) {
                    if (model.getBoard().getBoard()[y][x].getTeam() != model.getBoard().getBoard()[y-1][x].getTeam())
                        range.add(new int[]{y - 1, x});
                }
            }
            case QUEEN -> {
                pieceInRange = false;
                p = y - 1;
                while ((p > -1) && !pieceInRange) {
                    if (model.getBoard().getBoard()[p][x] != null) {
                        if (model.getBoard().getBoard()[y][x].getTeam() != model.getBoard().getBoard()[p][x].getTeam())
                            range.add(new int[]{p, x});
                        pieceInRange = true;
                    }
                    p--;
                }
                pieceInRange = false;
                p = y + 1;
                while ((p < 8) && !pieceInRange) {
                    if (model.getBoard().getBoard()[p][x] != null) {
                        if (model.getBoard().getBoard()[y][x].getTeam() != model.getBoard().getBoard()[p][x].getTeam())
                            range.add(new int[]{p, x});
                        pieceInRange = true;
                    }
                    p++;
                }

                pieceInRange = false;
                p = x - 1;
                while ((p > -1) && !pieceInRange) {
                    if (model.getBoard().getBoard()[y][p] != null) {
                        if (model.getBoard().getBoard()[y][x].getTeam() != model.getBoard().getBoard()[y][p].getTeam())
                            range.add(new int[]{y, p});
                        pieceInRange = true;
                    }
                    p--;
                }
                pieceInRange = false;
                p = x + 1;
                while ((p < 8) && !pieceInRange) {
                    if (model.getBoard().getBoard()[y][p] != null) {
                        if (model.getBoard().getBoard()[y][x].getTeam() != model.getBoard().getBoard()[y][p].getTeam())
                            range.add(new int[]{y, p});
                        pieceInRange = true;
                    }
                    p++;
                }

                pieceInRange = false;
                p = y - 1;
                q = x - 1;
                while ((p > -1) && (q > -1) && !pieceInRange) {
                    if (model.getBoard().getBoard()[p][q] != null) {
                        if (model.getBoard().getBoard()[y][x].getTeam() != model.getBoard().getBoard()[p][q].getTeam())
                            range.add(new int[]{p, q});
                        pieceInRange = true;
                    }
                    p--;
                    q--;
                }
                pieceInRange = false;
                p = y + 1;
                q = x + 1;
                while ((p < 8) && (q < 8) && !pieceInRange) {
                    if (model.getBoard().getBoard()[p][q] != null) {
                        if (model.getBoard().getBoard()[y][x].getTeam() != model.getBoard().getBoard()[p][q].getTeam())
                            range.add(new int[]{p, q});
                        pieceInRange = true;
                    }
                    p++;
                    q++;
                }
                pieceInRange = false;
                p = y + 1;
                q = x - 1;
                while ((p < 8) && (q > -1) && !pieceInRange) {
                    if (model.getBoard().getBoard()[p][q] != null) {
                        if (model.getBoard().getBoard()[y][x].getTeam() != model.getBoard().getBoard()[p][q].getTeam())
                            range.add(new int[]{p, q});
                        pieceInRange = true;
                    }
                    p++;
                    q--;
                }
                pieceInRange = false;
                p = y - 1;
                q = x + 1;
                while ((p > -1) && (q < 8) && !pieceInRange) {
                    if (model.getBoard().getBoard()[p][q] != null) {
                        if (model.getBoard().getBoard()[y][x].getTeam() != model.getBoard().getBoard()[p][q].getTeam())
                            range.add(new int[]{p, q});
                        pieceInRange = true;
                    }
                    p--;
                    q++;
                }
            }
        }

        return range;
    }

    private ArrayList<int[]> getTotalRange() {
        ArrayList<int[]> range = new ArrayList<>();

        range.addAll(freeRange);
        range.addAll(attackRange);

        return range;
    }

    private void highLightFreeRange(ArrayList<int[]> range) {
        for(int[] coordinate : range) {
            frame.getBoardFrame()[coordinate[0]][coordinate[1]].setBackground(new Color(137, 207, 240));
        }
    }

    private void highlightAttackRange(ArrayList<int[]> range) {
        for(int[] coordinate : range) {
            frame.getBoardFrame()[coordinate[0]][coordinate[1]].setBackground(new Color(240, 137, 127));
        }
    }
}