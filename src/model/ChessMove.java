package model;

public class ChessMove {
    private ChessBoardLocation src;
    private ChessBoardLocation dest;

    public ChessMove(){ }

    public ChessBoardLocation getSrc() { return src; }
    public ChessBoardLocation getDest() { return dest; }

    public void setSrc(ChessBoardLocation src) { this.src = src; }
    public void setDest(ChessBoardLocation dest) { this.dest = dest; }
}
