package lk.ijse.dep.service;
import static lk.ijse.dep.service.Piece.*;

public class BoardImpl implements Board {
    private final Piece[][] pieces;
    private final BoardUI boardUI;

    public BoardImpl(BoardUI boardUI) {
        this.boardUI = boardUI;
        pieces = new Piece[NUM_OF_COLS][NUM_OF_ROWS];
        for (int col = 0; col < NUM_OF_COLS; col++) {
            for (int row = 0; row <NUM_OF_ROWS ; row++) {
                pieces[col][row] = EMPTY;
            }
        }
    }

    @Override
    public BoardUI getBoardUI() {
        return boardUI;
    }

    @Override
    public int findNextAvailableSpot(int col) {
        for (int row = 0; row < NUM_OF_ROWS; row++) {
            if (pieces[col][row] == EMPTY) {
                return row;
            }
        }
        return -1;
    }

    @Override
    public boolean isLegalMove(int col) {
        for (int row = 0; row < NUM_OF_ROWS; row++) {
            if (pieces[col][row] == EMPTY) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean existLegalMoves() {
        for (int col = 0; col < NUM_OF_COLS; col++) {
            if (isLegalMove(col)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void updateMove(int col, Piece move) {
        pieces[col][findNextAvailableSpot(col)] = move;
    }

    @Override
    public void updateMove(int col, int row, Piece move) {
        pieces[col][row] = move;
    }

    @Override
    public Winner findWinner() {
        Winner winner=new Winner(EMPTY);

        for (int col = 0; col < NUM_OF_COLS; col++) {
            for (int row = 0; row < 2; row++) {
                if (pieces[col][row] == pieces[col][row+1] && pieces[col][row+1] == pieces[col][row+2] && pieces[col][row+2] == pieces[col][row+3]) {
                    if(pieces[col][row] == BLUE)
                        winner = new Winner(BLUE, col, row, col, row+3);
                    if(pieces[col][row] == GREEN)
                        winner = new Winner(GREEN, col, row, col, row+3);
                }
            }
        }
        for (int row = 0; row < NUM_OF_ROWS; row++) {
            for (int col = 0; col < 3; col++) {
                if (pieces[col][row] == pieces[col+1][row] && pieces[col+1][row] == pieces[col+2][row] && pieces[col+2][row] == pieces[col+3][row]) {
                    if(pieces[col][row] == BLUE)
                        winner = new Winner(BLUE, col, row, col+3, row);
                    if(pieces[col][row] == GREEN)
                        winner = new Winner(GREEN, col, row, col+3, row);
                }
            }
        }
        return winner;
    }
}