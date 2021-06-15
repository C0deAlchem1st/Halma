package controller;

import mainmenu.ShowError;
import model.ChessBoardLocation;
import model.ChessPiece;
import view.GameFrame;

import java.awt.*;

public class CheckError {
    GameController controller;
    boolean out;
    int redChess;
    int greenChess;
    int blackChess;
    int whiteChess;
    int blank;
    GameFrame gameFrame;

    public CheckError(GameController controller, boolean out, GameFrame gameFrame){
        this.controller = controller;
        this.out = out;
        redChess = 0;
        greenChess = 0;
        blackChess = 0;
        whiteChess = 0;
        blank = 0;
        this.gameFrame = gameFrame;
    }

    public void work(){
        String error1 = "";
        String error2 = "";
        String error3 = "";
        String error4 = "";
        if(out) error1 += "棋子越界";

        for(int row = 0; row < controller.getModel().getDimension(); row++){
            for(int col = 0; col < controller.getModel().getDimension(); col++){
                ChessPiece piece = controller.getModel().getChessPieceAt(new ChessBoardLocation(row, col));
                if(piece == null) blank++;
                else if(piece.getColor().equals(Color.RED)) redChess++;
                else if(piece.getColor().equals(Color.GREEN)) greenChess++;
                else if(piece.getColor().equals(Color.BLACK)) blackChess++;
                else whiteChess++;
            }
        }

        int total = 0;
        if(redChess > 0) total++;
        if(greenChess > 0) total++;
        if(blackChess > 0) total++;
        if(whiteChess > 0) total++;

        if(controller.getPlayerNum() == 2){
            if(!(redChess == greenChess)) error2 += "\n棋子数量不等";
            if(total != 2) error3 += "\n玩家数量不等";
        }
        else{
            if(!(redChess == greenChess && redChess == blackChess && redChess == whiteChess)) error2 += "\n棋子数量不等";
            if(total != 4) error3 += "\n玩家数量不等";
        }

        if(winAtStart()) error4 += "\n开局获胜";

        if((!error1.equals("")) || (!error2.equals("")) || (!error3.equals("")) || (!error4.equals(""))){
            ShowError showError = new ShowError(error1, error2, error3, error4, gameFrame);
            showError.work();
        }
    }

    public boolean winAtStart(){
        int[] chess;
        if(controller.getPlayerNum() == 2){
            chess = new int[5];
            for(ChessBoardLocation location : controller.getModel().getList(0)){
                chess[toInt(controller.getModel().getChessPieceAt(location))]++;
            }
            if(chess[1] == 19) return true;

            chess = new int[5];
            for(ChessBoardLocation location : controller.getModel().getList(1)){
                chess[toInt(controller.getModel().getChessPieceAt(location))]++;
            }
            if(chess[0] == 19) return true;
        }
        else{
            for(int i = 0; i < 4; i++){
                chess = new int[5];
                for(ChessBoardLocation location : controller.getModel().getList(i)){
                    chess[toInt(controller.getModel().getChessPieceAt(location))]++;
                }
                for(int k = 0; k < 4; k++) if(i != k && chess[k] == 13) return true;
            }
        }
        return false;
    }

    public int toInt(ChessPiece piece){
        if(piece == null) return 4;
        if(piece.getColor().equals(Color.RED)) return 0;
        if(piece.getColor().equals(Color.GREEN)) return 1;
        if(piece.getColor().equals(Color.BLACK)) return 2;
        return 3;
    }
}
