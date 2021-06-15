package model;

import java.awt.*;
import java.util.*;

import mainmenu.SaveFile;

public class ChessBoard {
    private Square[][] grid;
    private int dimension;
    private ArrayList<ChessBoardLocation> redList = new ArrayList<>();
    private ArrayList<ChessBoardLocation> greenList = new ArrayList<>();
    private ArrayList<ChessBoardLocation> blackList = new ArrayList<>();
    private ArrayList<ChessBoardLocation> whiteList = new ArrayList<>();

    public ChessBoard(int dimension, int num, boolean init) {
        this.grid = new Square[dimension][dimension];
        this.dimension = dimension;

        initGrid(num);
        if(init) initPieces(num);
    }

    private void initGrid(int num) {
        for (int i = 0; i < dimension; i++) {
            for (int j = 0; j < dimension; j++) {
                grid[i][j] = new Square(new ChessBoardLocation(i, j));
            }
        }

        if(num == 2){
            for(int i = 0; i < 5; i++){
                redList.add(new ChessBoardLocation(0, i));
            }
            for(int i = 0; i < 5; i++){
                redList.add(new ChessBoardLocation(1, i));
            }
            for(int i = 0; i < 4; i++){
                redList.add(new ChessBoardLocation(2, i));
            }
            for(int i = 0; i < 3; i++){
                redList.add(new ChessBoardLocation(3, i));
            }
            for(int i = 0; i < 2; i++){
                redList.add(new ChessBoardLocation(4, i));
            }

            for(int i = dimension - 5; i < dimension; i++){
                greenList.add(new ChessBoardLocation(dimension - 1, i));
            }
            for(int i = dimension - 5; i < dimension; i++){
                greenList.add(new ChessBoardLocation(dimension - 2, i));
            }
            for(int i = dimension - 4; i < dimension; i++){
                greenList.add(new ChessBoardLocation(dimension - 3, i));
            }
            for(int i = dimension - 3; i < dimension; i++){
                greenList.add(new ChessBoardLocation(dimension - 4, i));
            }
            for(int i = dimension - 2; i < dimension; i++){
                greenList.add(new ChessBoardLocation(dimension - 5, i));
            }
        }
        if(num == 4){
            for(int i = 0; i < 4; i++) redList.add(new ChessBoardLocation(0, i));
            for(int i = 0; i < 4; i++) redList.add(new ChessBoardLocation(1, i));
            for(int i = 0; i < 3; i++) redList.add(new ChessBoardLocation(2, i));
            for(int i = 0; i < 2; i++) redList.add(new ChessBoardLocation(3, i));

            for(int i = dimension - 4; i < dimension; i++) whiteList.add(new ChessBoardLocation(0, i));
            for(int i = dimension - 4; i < dimension; i++) whiteList.add(new ChessBoardLocation(1, i));
            for(int i = dimension - 3; i < dimension; i++) whiteList.add(new ChessBoardLocation(2, i));
            for(int i = dimension - 2; i < dimension; i++) whiteList.add(new ChessBoardLocation(3, i));

            for(int i = 0; i < 2; i++) greenList.add(new ChessBoardLocation(dimension - 4, i));
            for(int i = 0; i < 3; i++) greenList.add(new ChessBoardLocation(dimension - 3, i));
            for(int i = 0; i < 4; i++) greenList.add(new ChessBoardLocation(dimension - 2, i));
            for(int i = 0; i < 4; i++) greenList.add(new ChessBoardLocation(dimension - 1, i));

            for(int i = dimension - 4; i < dimension; i++) blackList.add(new ChessBoardLocation(dimension - 1, i));
            for(int i = dimension - 4; i < dimension; i++) blackList.add(new ChessBoardLocation(dimension - 2, i));
            for(int i = dimension - 3; i < dimension; i++) blackList.add(new ChessBoardLocation(dimension - 3, i));
            for(int i = dimension - 2; i < dimension; i++) blackList.add(new ChessBoardLocation(dimension - 4, i));
        }
    }

    private void initPieces(int num) {
        // TODO: This is only a demo implementation.
        if(num == 2){
            for(int i = 0; i < 5; i++){
                grid[0][i].setPiece(new ChessPiece(Color.RED));
            }
            for(int i = 0; i < 5; i++){
                grid[1][i].setPiece(new ChessPiece(Color.RED));
            }
            for(int i = 0; i < 4; i++){
                grid[2][i].setPiece(new ChessPiece(Color.RED));
            }
            for(int i = 0; i < 3; i++){
                grid[3][i].setPiece(new ChessPiece(Color.RED));
            }
            for(int i = 0; i < 2; i++){
                grid[4][i].setPiece(new ChessPiece(Color.RED));
            }

            for(int i = dimension - 5; i < dimension; i++){
                grid[dimension - 1][i].setPiece(new ChessPiece(Color.GREEN));
            }
            for(int i = dimension - 5; i < dimension; i++){
                grid[dimension - 2][i].setPiece(new ChessPiece(Color.GREEN));
            }
            for(int i = dimension - 4; i < dimension; i++){
                grid[dimension - 3][i].setPiece(new ChessPiece(Color.GREEN));
            }
            for(int i = dimension - 3; i < dimension; i++){
                grid[dimension - 4][i].setPiece(new ChessPiece(Color.GREEN));
            }
            for(int i = dimension - 2; i < dimension; i++){
                grid[dimension - 5][i].setPiece(new ChessPiece(Color.GREEN));
            }
        }
        if(num == 4){
            for(int i = 0; i < 4; i++){
                grid[0][i].setPiece(new ChessPiece(Color.RED));
            }
            for(int i = 0; i < 4; i++){
                grid[1][i].setPiece(new ChessPiece(Color.RED));
            }
            for(int i = 0; i < 3; i++){
                grid[2][i].setPiece(new ChessPiece(Color.RED));
            }
            for(int i = 0; i < 2; i++){
                grid[3][i].setPiece(new ChessPiece(Color.RED));
            }

            for(int i = dimension - 4; i < dimension; i++){
                grid[0][i].setPiece(new ChessPiece(Color.WHITE));
            }
            for(int i = dimension - 4; i < dimension; i++){
                grid[1][i].setPiece(new ChessPiece(Color.WHITE));
            }
            for(int i = dimension - 3; i < dimension; i++){
                grid[2][i].setPiece(new ChessPiece(Color.WHITE));
            }
            for(int i = dimension - 2; i < dimension; i++){
                grid[3][i].setPiece(new ChessPiece(Color.WHITE));
            }

            for(int i = 0; i < 2; i++){
                grid[dimension - 4][i].setPiece(new ChessPiece(Color.GREEN));
            }
            for(int i = 0; i < 3; i++){
                grid[dimension - 3][i].setPiece(new ChessPiece(Color.GREEN));
            }
            for(int i = 0; i < 4; i++){
                grid[dimension - 2][i].setPiece(new ChessPiece(Color.GREEN));
            }
            for(int i = 0; i < 4; i++){
                grid[dimension - 1][i].setPiece(new ChessPiece(Color.GREEN));
            }

            for(int i = dimension - 4; i < dimension; i++){
                grid[dimension - 1][i].setPiece(new ChessPiece(Color.BLACK));
            }
            for(int i = dimension - 4; i < dimension; i++){
                grid[dimension - 2][i].setPiece(new ChessPiece(Color.BLACK));
            }
            for(int i = dimension - 3; i < dimension; i++){
                grid[dimension - 3][i].setPiece(new ChessPiece(Color.BLACK));
            }
            for(int i = dimension - 2; i < dimension; i++){
                grid[dimension - 4][i].setPiece(new ChessPiece(Color.BLACK));
            }
        }
    }

    public Square getGridAt(ChessBoardLocation location) {
        return grid[location.getRow()][location.getColumn()];
    }
    public Square getGridAt(int row, int col){ return grid[row][col];}

    public ChessPiece getChessPieceAt(ChessBoardLocation location) {
        return getGridAt(location).getPiece();
    }

    public void setChessPieceAt(ChessBoardLocation location, ChessPiece piece) {
        getGridAt(location).setPiece(piece);
        SaveFile.updateChessBoard(location.getColumn(), location.getRow(), piece);
    }

    public ChessPiece removeChessPieceAt(ChessBoardLocation location) {
        ChessPiece piece = getGridAt(location).getPiece();
        getGridAt(location).setPiece(null);
        SaveFile.updateChessBoard(location.getColumn(), location.getRow(), null);
        return piece;
    }

    public void moveChessPiece(ChessBoardLocation src, ChessBoardLocation dest) {
        ChessPiece oldPiece = removeChessPieceAt(src);
        setChessPieceAt(dest, oldPiece);
        SaveFile.updateChessBoard(dest.getColumn(), dest.getRow(), oldPiece);
    }

    public int getDimension() {
        return dimension;
    }

    public ArrayList<ChessBoardLocation> getList(int colorNum){
        if(colorNum == 0) return redList;
        if(colorNum == 1) return greenList;
        if(colorNum == 2) return blackList;
        if(colorNum == 3) return whiteList;
        return null;
    }

    public boolean isValidMove(ChessBoardLocation src, ChessBoardLocation dest) {
        boolean check = true;
        // 1
        if (getChessPieceAt(src) == null || getChessPieceAt(dest) != null) check = false;
        // 2
        int srcRow = src.getRow(), srcCol = src.getColumn(), destRow = dest.getRow(), destCol = dest.getColumn();
        int rowDistance = destRow - srcRow, colDistance = destCol - srcCol;
        if (Math.abs(rowDistance) > 1 && Math.abs(colDistance) > 1) return false;
        // 3

        return check;
    }




}// end of class
