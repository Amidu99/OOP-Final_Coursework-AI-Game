package lk.ijse.dep.service;

import static lk.ijse.dep.service.Piece.*;

public class AiPlayer extends Player {
    public int value;
    public AiPlayer(Board board) {
        this.board=board;
    }

    public void movePiece(int col) {
        col = searchCol();
        this.board.updateMove(col, GREEN);
        this.board.getBoardUI().update(col, false);
        Winner winner = this.board.findWinner();
        if (winner.getWinningPiece() != EMPTY) {
            this.board.getBoardUI().notifyWinner(winner);
        } else if (!this.board.existLegalMoves()) {
            this.board.getBoardUI().notifyWinner(new Winner(EMPTY));
        }
    }

    private int searchCol() {
        /*The main objective of this method is to block the player from winning.it checks for
        available winning cases and prevent it by placing the piece to the position that can block the win of the
        human player, the AI will automatically focus on winning, it will use the minimax again, it will search for a
        possible winning state, but if there is a user winning situation and the AI winning situation in the board,
        if the AI is playing, it will automatically skip the user blocking operation, and it will place the piece at the
        AI winning possible place of the board. if there is no winning places in the board, the searchCol will return
        a random value by the Math.random() */
        int tiedColumn = 0;
        boolean HumanWinningChance = false;
        for (int col = 0; col < 6; col++) {//searching for a tied column
            if (this.board.isLegalMove(col) && this.board.existLegalMoves()) {
                int row = this.board.findNextAvailableSpot(col);
                this.board.updateMove(col, GREEN);
                int evalue = minimax(0, false);
                this.board.updateMove(col, row, EMPTY);
                if (evalue == 1) {
                    value = 0;
                    return col;
                }
                if (evalue == -1) {
                    HumanWinningChance = true;
                }else {
                    tiedColumn = col;
                }
            }
        }
        if ((HumanWinningChance) && (this.board.isLegalMove(tiedColumn))) {
            /*checking is there is a user winning status
             and legal move at the tied column of the board*/
            value = 0;
            return tiedColumn;
        }
        int col;

        do {
            col = (int) (Math.random() * 6);
        }while (!this.board.isLegalMove(col));
        value = 0;
        return col;
    }
    /*if none of those happened, it will generate a random number that is legal to move on the board,
    and returns the value to the movePiece method.*/

    private int minimax(int depth, boolean maximizingPlayer) {
        value++;
        Winner winner = this.board.findWinner();
        if (winner.getWinningPiece() == GREEN) {
            return 1;
        }
        if (winner.getWinningPiece() == BLUE) {
            return -1;
        }
        if ((!this.board.existLegalMoves()) || (depth == 2)) {
            return 0;
        }
        if (this.board.existLegalMoves()) {
            if (maximizingPlayer) {
                for (int col = 0; col < 6; col++)
                    if (this.board.isLegalMove(col)) {
                        int row = this.board.findNextAvailableSpot(col);
                        this.board.updateMove(col, GREEN);//update the move
                        int evalue = minimax(depth + 1,false);
                        this.board.updateMove(col, row, EMPTY);
                        if (evalue == 1) {
                            return evalue;
                        }
                    }
            } else {
                for (int col = 0; col < 6; col++) {
                    if (this.board.isLegalMove(col)) {
                        int row = this.board.findNextAvailableSpot(col);
                        this.board.updateMove(col, BLUE);
                        int evalue = minimax(depth + 1, true);
                        this.board.updateMove(col, row, EMPTY);
                        if (evalue == -1) {
                            return evalue;
                        }
                    }
                }
            }
        }
        return 0;
    }
}