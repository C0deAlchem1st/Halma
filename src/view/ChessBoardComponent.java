package view;


import listener.GameListener;
import model.ChessBoardLocation;

import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;
import javax.swing.*;
import java.awt.*;

public class ChessBoardComponent extends JComponent {
    private static final Color BOARD_COLOR_1 = new Color(255, 255, 204);
    private static final Color BOARD_COLOR_2 = new Color(170, 170, 170);

    private Color boardColor1 = new Color(255, 255, 204);
    private Color boardColor2 = new Color(170, 170, 170);

    private List<GameListener> listenerList = new ArrayList<>();
    private SquareComponent[][] gridComponents;
    private int dimension;
    private int gridSize;

    private Color guideColor = new Color(0,128,255,128);
    public void setGuideColor(Color guideColor) { this.guideColor = guideColor; }

    public ChessBoardComponent(int size, int dimension, Color color1, Color color2) {
        enableEvents(AWTEvent.MOUSE_EVENT_MASK);
        setLayout(null); // Use absolute layout.
        setLocation(0, 30);
        setSize(size, size);

        boardColor1 = color1;
        boardColor2 = color2;

        this.gridComponents = new SquareComponent[dimension][dimension];
        this.dimension = dimension;
        this.gridSize = size / dimension;
        initGridComponents();
    }

    private void initGridComponents() {

        for (int row = 0; row < dimension; row++) {
            for (int col = 0; col < dimension; col++) {
                gridComponents[row][col] = new SquareComponent(gridSize, row, col,
                        (row + col) % 2 == 0 ? boardColor1 : boardColor2);
                gridComponents[row][col].setLocation(row * gridSize, col * gridSize);
                if ( !gridComponents[row][col].isBoundary() ) add(gridComponents[row][col]);
            }
        }
        //阵营边框
        for (int row = 0; row < dimension; row++) {
            for (int col = 0; col < dimension; col++) {
                if ( gridComponents[row][col].isBoundary() ) add(gridComponents[row][col]);
            }
        }
    }

    public SquareComponent getGridAt(ChessBoardLocation location) {
        return gridComponents[location.getRow()][location.getColumn()];
    }

    public void setChessAtGrid(ChessBoardLocation location, Color color) {
        removeChessAtGrid(location);
        // 添加了棋子组件！
        getGridAt(location).add(new ChessComponent(color));
    }

    public void removeChessAtGrid(ChessBoardLocation location) {
        // Note re-validation is required after remove / removeAll.
        getGridAt(location).removeAll();
        getGridAt(location).revalidate();
    }

    public void paintGrid(ChessBoardLocation location){
        int row = location.getRow();
        int col = location.getColumn();
        gridComponents[row][col].setSecondColor(gridComponents[row][col].getColor());
        gridComponents[row][col].setColor(guideColor);
        gridComponents[row][col].repaint();
    }

    public void recoverGrid(ChessBoardLocation location){
        int row = location.getRow();
        int col = location.getColumn();
        gridComponents[row][col].setColor(gridComponents[row][col].getSecondColor());
        gridComponents[row][col].repaint();
    }

    private ChessBoardLocation getLocationByPosition(int x, int y) {
        return new ChessBoardLocation(x / gridSize, y / gridSize);
    }

    @Override
    protected void processMouseEvent(MouseEvent e) {
        super.processMouseEvent(e);

        if (e.getID() == MouseEvent.MOUSE_PRESSED) {
            JComponent clickedComponent = (JComponent) getComponentAt(e.getX(), e.getY());
            ChessBoardLocation location = getLocationByPosition(e.getX(), e.getY());
            for (GameListener listener : listenerList) {
                if (clickedComponent.getComponentCount() == 0) {
                    listener.onPlayerClickSquare(location, (SquareComponent) clickedComponent);
                } else {
                    listener.onPlayerClickChessPiece(location, (ChessComponent) clickedComponent.getComponent(0));
                }
            }
        }
    }

    public void registerListener(GameListener listener) {
        listenerList.add(listener);
    }

}// end of class
