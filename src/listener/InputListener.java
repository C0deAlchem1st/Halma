package listener;

import model.ChessBoardLocation;
import view.ChessComponent;
import view.SquareComponent;

public interface InputListener extends Listener {
    void onPlayerClickSquare(ChessBoardLocation location, SquareComponent component);

    void onPlayerClickChessPiece(ChessBoardLocation location, ChessComponent component);
}
