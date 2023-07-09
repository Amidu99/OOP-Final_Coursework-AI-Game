package lk.ijse.dep.service;

public abstract class Player {
    protected Board board;
    
    public Player() {}
    public abstract void movePiece(int col);
}