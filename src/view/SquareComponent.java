package view;

import javax.swing.*;
import java.awt.*;

public class SquareComponent extends JPanel {
    private Color color;
    private int row;
    private int col;

    public Color getColor() { return color; }
    public void setColor(Color color) { this.color = color; }
    private Color secondColor;
    public Color getSecondColor() { return secondColor; }
    public void setSecondColor(Color secondColor) { this.secondColor = secondColor; }

    public SquareComponent(int size, int row, int col, Color color) {
        setLayout(new GridLayout(1, 1)); // Use 1x1 grid layout.
        setSize(size, size);
        this.color = color;
        this.row = row;
        this.col = col;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        paintSquare(g);
    }

    private void paintSquare(Graphics g) {
        g.setColor( color );
        g.fillRect(0, 0, getWidth(), getHeight());
        g.setColor( isBoundary() ? Color.BLUE : Color.BLACK );
        g.drawRect(0, 0, getWidth(), getHeight());
        if ( isBoundary() ) {
            g.drawRoundRect(1,1,getWidth()-3,getHeight()-3,3,3);
            g.drawRoundRect(2,2,getWidth()-4,getHeight()-4,4,4);
            g.drawRoundRect(3,3,getWidth()-5,getHeight()-5,5,5);
            g.drawRoundRect(3,3,getWidth()-6,getHeight()-6,6,6);
        }
    }

    //绘制阵营边界
    public boolean isBoundary(){
        boolean check = false;
        // 1
        if ( row == 0 && col == 4 ) check = true;
        if ( row == 1 && col == 4 ) check = true;
        if ( row == 2 && col == 3 ) check = true;
        if ( row == 3 && col == 2 ) check = true;
        if ( row == 4 && col == 1 ) check = true;
        if ( row == 4 && col == 0 ) check = true;
        // 2
        if ( row == 14 && col == 0 ) check = true;
        if ( row == 14 && col == 1 ) check = true;
        if ( row == 15 && col == 2 ) check = true;
        if ( row == 16 && col == 3 ) check = true;
        if ( row == 17 && col == 4 ) check = true;
        if ( row == 18 && col == 4 ) check = true;
        // 3
        if ( row == 14 && col == 18 ) check = true;
        if ( row == 14 && col == 17 ) check = true;
        if ( row == 15 && col == 16 ) check = true;
        if ( row == 16 && col == 15 ) check = true;
        if ( row == 17 && col == 14 ) check = true;
        if ( row == 18 && col == 14 ) check = true;
        // 4
        if ( row == 0 && col == 14 ) check = true;
        if ( row == 1 && col == 14 ) check = true;
        if ( row == 2 && col == 15 ) check = true;
        if ( row == 3 && col == 16 ) check = true;
        if ( row == 4 && col == 17 ) check = true;
        if ( row == 4 && col == 18 ) check = true;
        return check;
    }
}
