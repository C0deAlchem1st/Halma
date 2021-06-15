package mainmenu;

import model.ChessBoard;
import model.ChessBoardLocation;
import model.ChessPiece;
import model.Square;

import java.util.ArrayList;

public class SaveFile {
    private int valid; // 0表示空存档，1表示正常存档
    private int index; // 存档编号
    private int year;
    private int month;
    private int day;
    private int playerNum; // 玩家人数
    private int mode; // 0表示个人，1表示团队
    private int turn; // 当前回合玩家的编号,从0开始
    public ArrayList<String> playerNames = new ArrayList<>();
    public static Square[][] grid = new Square[19][19];

    public SaveFile( ){ }

    public static void updateChessBoard(int x, int y, ChessPiece piece){
        ChessBoardLocation location = new ChessBoardLocation(x, y);
        Square newGrid = new Square(location);
        newGrid.setPiece(piece);
        grid[x][y] = newGrid;
    }


    public String showInformation(){
        String modeName;
        if (mode==0) modeName = "个人";
        else modeName = "团队";

        String inform;
        if ( valid == 0 ) inform = "空存档";
        else inform = "<html>存档" + index + " " + year + "/" + month + "/" + day + "<br>"
                + playerNum + "人 " + modeName + " 当前回合：" + playerNames.get(turn) + "</html>";
        return inform;
    }

    // getters and setters
    public int getValid() { return valid; }
    public int getIndex() { return index; }
    public int getYear() { return year; }
    public int getMonth() { return month; }
    public int getDay() { return day; }
    public int getPlayerNum() { return playerNum; }
    public int getMode() { return mode; }
    public int getTurn() { return turn; }

    public void setValid(int valid) { this.valid = valid; }
    public void setIndex(int index) { this.index = index; }
    public void setYear(int year) { this.year = year; }
    public void setMonth(int month) { this.month = month; }
    public void setDay(int day) { this.day = day; }
    public void setPlayerNum(int playerNum) { this.playerNum = playerNum; }
    public void setMode(int mode) { this.mode = mode; }
    public void setTurn(int turn) { this.turn = turn; }

}// end of class
