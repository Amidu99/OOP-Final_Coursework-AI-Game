package lk.ijse.dep.service;
import static lk.ijse.dep.service.Piece.*;

public class HumanPlayer extends Player {

    public HumanPlayer(Board board) {
        this.board=board;
    }

    @Override
    public void movePiece(int col){
        if (board.isLegalMove(col)) {
            board.updateMove(col, BLUE);
            board.getBoardUI().update(col, true);
            if (board.findWinner().getWinningPiece() != EMPTY) {
                board.getBoardUI().notifyWinner(board.findWinner());
            } else if (!board.existLegalMoves()) {
                board.getBoardUI().notifyWinner(new Winner(EMPTY));
            }
        }
    }
}