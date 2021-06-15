package model;

import java.awt.*;

public class Undo {
    private Undo lastStep;

    private ChessBoardLocation start;
    private ChessBoardLocation end;
    private Color color;

    public Undo(ChessBoardLocation start, ChessBoardLocation end, Color color){
        this.start = start;
        this.end = end;
        this.color = color;
    }

    public Undo(){}

    public Undo getLastStep(){
        if (lastStep!=null) return lastStep;
        else return null;
    }

    public void setLastStep( Undo lastStep ){ this.lastStep = lastStep; }

    public ChessBoardLocation getStart() { return start; }
    public ChessBoardLocation getEnd() { return end; }
    public Color getColor() { return color; }

    public void setStart(ChessBoardLocation start) { this.start = start; }
    public void setEnd(ChessBoardLocation end) { this.end = end; }
    public void setColor(Color color) { this.color = color; }

}// end of class
