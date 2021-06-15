package controller;

import listener.GameListener;
import model.*;
import music.Music;
import view.*;

import java.awt.*;
import java.util.ArrayList;

public class GameController implements GameListener {
    public static int[][] direction2 = {{-2, -2}, {0, -2}, {2, -2}, {-2, 0}, {2, 0}, {-2, 2}, {0, 2}, {2, 2}};
    public static int[][] direction1 = {{-1, -1}, {0, -1}, {1, -1}, {-1, 0}, {1, 0}, {-1, 1}, {0, 1}, {1, 1}};
    private ChessBoardComponent view;
    private ChessBoard model;

    private Color currentPlayer;
    private int currentPlayerNum;
    private int playerNum;
    private ChessPiece selectedPiece;
    private ChessBoardLocation selectedLocation;
    private Color[][] belong;
    public static Color[] chessColor = {Color.RED, Color.GREEN, Color.BLACK, Color.WHITE};
    private GameFrame gameFrame;

    private boolean isFirst = true;

    private boolean SoundOn = true;
    public boolean isSoundOn() { return SoundOn; }
    public void setSoundOn(boolean soundOn) { SoundOn = soundOn; }

    private boolean isAIMode = false;
    public void setAIMode(boolean AIMode) { isAIMode = AIMode; }
    public boolean isAIMode() { return isAIMode; }

    private boolean isMoveGuideOn = false;
    public boolean isMoveGuideOn() { return isMoveGuideOn; }
    public void setMoveGuideOn(boolean isMoveGuideOn){ this.isMoveGuideOn = isMoveGuideOn; }
    public void setViewGuideColor(Color color){ view.setGuideColor(color); }


    public void setGameFrame(GameFrame gameFrame) { this.gameFrame = gameFrame; }
    public GameFrame getGameFrame() { return gameFrame; }
    public ChessBoard getModel() { return model; }
    public ChessBoardComponent getView() { return view; }

    public int getPlayerNum() { return playerNum; }

    public GameController(ChessBoardComponent boardComponent, ChessBoard chessBoard, int playerNum, int currentPlayerNum) {
        this.view = boardComponent;
        this.model = chessBoard;

        this.currentPlayerNum = currentPlayerNum;
        checkCurrentPlayer(this.currentPlayerNum);
        this.playerNum = playerNum;
        view.registerListener(this);
        initGameState();

        belong = new Color[model.getDimension()][model.getDimension()];
        setBelong(playerNum);

    }

    public void checkCurrentPlayer(int num){
        switch (num){
            case 0 : setCurrentPlayer(Color.RED); break;
            case 1 : setCurrentPlayer(Color.GREEN); break;
            case 2 : setCurrentPlayer(Color.BLACK); break;
            default: setCurrentPlayer(Color.WHITE); break;
        }
    }

    public void initGameState() {
        for (int row = 0; row < model.getDimension(); row++) {
            for (int col = 0; col < model.getDimension(); col++) {
                ChessBoardLocation location = new ChessBoardLocation(row, col);
                ChessPiece piece = model.getChessPieceAt(location);
                if (piece != null) {
                    view.setChessAtGrid(location, piece.getColor());
                }
            }
        }
        view.repaint();
    }

    public void nextPlayer(int currentPlayerNum, boolean isAIMove) {
        if(isAIMode){
            // AI走棋
            if(isAIMove) computerTurn();
            else setCurrentPlayer(Color.RED);
            return;
        }
        setCurrentPlayerNum((currentPlayerNum + 1) % this.playerNum);
        switch (getCurrentPlayerNum()){
            case 0: setCurrentPlayer(Color.RED); break;
            case 1: setCurrentPlayer(Color.GREEN); break;
            case 2: setCurrentPlayer(Color.BLACK); break;
            default: setCurrentPlayer(Color.WHITE); break;
        }
    }

    public void deGuide(){
        if ( isMoveGuideOn && selectedPiece != null){
            for (ChessBoardLocation location : validMoveList) {
                view.recoverGrid(location);
            }
        }
    }
    public void showGuide(){
        if (isMoveGuideOn && selectedPiece != null){
            for (ChessBoardLocation location : validMoveList) view.paintGrid(location);
        }
    }
    public void deChosenMark(){
        if ( selectedPiece != null && selectedLocation != null ){
            deGuide();
            ChessBoardLocation location = selectedLocation;
            Color color = selectedPiece.getColor();
            setSelectedPiece(null);
            setSelectedLocation(null);

            view.removeChessAtGrid(location);
            view.setChessAtGrid(location, color);
            view.repaint();
        }
    }

    @Override
    public void onPlayerClickSquare(ChessBoardLocation location, SquareComponent component) {
        if(selectedLocation != null){
            if (!canMove(location)){
                throw new IllegalArgumentException("Illegal Halma Move");
            }
            else {
                //行棋指示
                ArrayList<ChessBoardLocation> dest = destination(selectedLocation);
                for(ChessBoardLocation each : dest){
                    //System.out.println("x = " + each.getRow() + "    y = " + each.getColumn());
                    if(each.getRow() == location.getRow() && each.getColumn() == location.getColumn()){
                        deGuide();
                        model.moveChessPiece(selectedLocation, location);
                        view.setChessAtGrid(location, selectedPiece.getColor());
                        view.removeChessAtGrid(selectedLocation);
                        view.repaint();
                        break;
                    }
                }

                //悔棋记录
                if (isFirst) isFirst = false;
                else {
                    Undo lastStep = new Undo(recordStep.getStart(),recordStep.getEnd(),recordStep.getColor());
                    lastStep.setLastStep(recordStep.getLastStep());
                    recordStep.setLastStep( lastStep );
                }
                recordStep.setStart(selectedLocation);
                recordStep.setEnd(location);
                recordStep.setColor(selectedPiece.getColor());

                // 胜利判断
                if(checkWin(location, false)){
                    //gameFrame.startMusic("sounds/win.wav");
                    gameFrame.startMusic("");
                    Victory victory = new Victory(getGameFrame(), getCurrentPlayerNum());
                    victory.setVisible(true);
                    gameFrame.setVisible(false);
                }

                // 胜利警告判断
                if ( isAboutToWin(nowPlayer(false), location) && isFirstPlayerToWin ) {
                    warning.audioClip.play();
                    //System.out.printf("Player %d is about to win!", nowPlayer(false) );
                    isFirstPlayerToWin = false;
                }

                // 显示当前走棋信息
                if ( currentPlayerNum % this.playerNum == 0) gameFrame.tf1.setText("MoveTo"+ location.toString());
                if ( currentPlayerNum % this.playerNum == 1) gameFrame.tf2.setText("MoveTo"+ location.toString());
                if ( currentPlayerNum % this.playerNum == 2) gameFrame.tf3.setText("MoveTo"+ location.toString());
                if ( currentPlayerNum % this.playerNum == 3) gameFrame.tf4.setText("MoveTo"+ location.toString());
                //显示当前回合玩家信息
                gameFrame.tf0.setText("Player"+ nowPlayer(true) );

                selectedPiece = null;
                selectedLocation = null;
                nextPlayer(getCurrentPlayerNum(), true);

                //音效
                if (SoundOn) startSound("sounds/move.wav");
                // AI走棋
                //if (isAIMode) computerTurn();
            }
        }// end of if selected != null
    }// end of method

    @Override
    public void onPlayerClickChessPiece(ChessBoardLocation location, ChessComponent component) {
        ChessPiece piece = model.getChessPieceAt(location);
        if (piece.getColor() == currentPlayer) {
            if (selectedPiece!=null) deChosenMark();
                selectedPiece = piece;
                selectedLocation = location;
                //显示走棋信息
                if (piece.getColor().equals(Color.RED))
                    gameFrame.tf1.setText("Pick " + location.toString());
                if (piece.getColor().equals(Color.GREEN))
                    gameFrame.tf2.setText("Pick " + location.toString());
                if (piece.getColor().equals(Color.BLACK))
                    gameFrame.tf3.setText("Pick " + location.toString());
                if (piece.getColor().equals(Color.WHITE))
                    gameFrame.tf4.setText("Pick " + location.toString());
            //显示可走位置
            component.setSelected(!component.isSelected());
            component.repaint();
            //显示可走棋
            updateList(selectedLocation);
            //System.out.println("recursion times: " + recursionTimes);
            recursionTimes = 0;
            //System.out.println("possible moves: " + validMoveList.size());
            //图形提示moveGuide
            showGuide();
            //音效
            if (SoundOn) startSound("sounds/pick.wav");
        }
    }

    public ArrayList<ChessBoardLocation> destination(ChessBoardLocation location){
        ArrayList<ChessBoardLocation> ans = new ArrayList<>();

        for(int i = 0; i < 8; i++){
            int newRow = location.getRow() + direction1[i][0];
            int newCol = location.getColumn() + direction1[i][1];
            if(newRow < 0 || newRow >= model.getDimension() || newCol < 0 || newCol >= model.getDimension()) continue;
            ChessBoardLocation newLocation = new ChessBoardLocation(newRow, newCol);

            if(model.getChessPieceAt(newLocation) == null) ans.add(newLocation);
        }

        ChessBoardLocation[] last = new ChessBoardLocation[500];
        ChessBoardLocation[] queue = new ChessBoardLocation[500];
        int l = 0; int r = 0;
        queue[r] = location;
        last[r++] = location;
        while(l < r){
            ChessBoardLocation now = queue[l];
            for(int i = 0; i < 8; i++){
                int newRow = now.getRow() + direction2[i][0];
                int newCol = now.getColumn() + direction2[i][1];
                if(newRow < 0 || newRow >= model.getDimension() || newCol < 0 || newCol >= model.getDimension()) continue;
                ChessBoardLocation newLocation = new ChessBoardLocation(newRow, newCol);
                if(model.getChessPieceAt(newLocation) != null) continue;
                boolean reach = false;
                for(int k = 0; k < r; k++){
                    if(last[k] != null && last[k].getRow() == newLocation.getRow() && last[k].getColumn() == newLocation.getColumn()) reach = true;
                }
                if(reach) continue;

                ChessBoardLocation midLocation = new ChessBoardLocation((newRow + now.getRow()) / 2, (newCol + now.getColumn()) / 2);

                if(model.getChessPieceAt(midLocation) == null) continue;

                ans.add(newLocation);
                queue[r] = newLocation;
                last[r++] = now;
            }
            l++;
        }

        return ans;
    }

    public int nowPlayer(boolean isUsedIntf0){
        int now = currentPlayerNum;
        if (isUsedIntf0) now += 2;
        else now += 1;
        if (now > playerNum) now = now % playerNum;
        if (now == 0) now = playerNum;
        return now;
    }

    public boolean checkWin(ChessBoardLocation chessBoardLocation, boolean isAIMove){
        Color now;
        if (isAIMove) now = Color.GREEN; //移动棋子的颜色
        else now = selectedPiece.getColor();  //移动棋子的颜色

        int target;
        Color targetColor;
        if(playerNum == 2){
            if(now.equals(Color.RED)){
                target = 1;
                targetColor = Color.GREEN;
            }
            else{
                target = 0;
                targetColor = Color.RED;
            }
        }
        else{
            if(now.equals(Color.RED)){
                target = 1;
                targetColor = Color.GREEN;
            }
            else if(now.equals(Color.GREEN)){
                target = 2;
                targetColor = Color.BLACK;
            }
            else if(now.equals(Color.BLACK)){
                target = 3;
                targetColor = Color.WHITE;
            }
            else{
                target = 0;
                targetColor = Color.RED;
            }
        }

        Color origin = belong[chessBoardLocation.getRow()][chessBoardLocation.getColumn()];  //初始该位置的颜色
        if(origin == null ) return false;
        if(!origin.equals(targetColor)) return false;

        for(ChessBoardLocation location : model.getList(target)){  //对手原始棋子的位置
            if(model.getChessPieceAt(location) == null) return false;  //现在对应位置的棋子
            if(!model.getChessPieceAt(location).getColor().equals(now)) return false;
        }
        return true;
    }

    private boolean isFirstPlayerToWin = true;
    public boolean isAboutToWin(int playerIndex, ChessBoardLocation nowLocation){
        int target = playerIndex;
        if(playerNum == 4 && target == 4) target = 0;
        if(playerNum == 2 && target == 2) target = 0;
        ArrayList<ChessBoardLocation> chessLocationList = model.getList(target);
        boolean contain = false;
        for(ChessBoardLocation location : chessLocationList){
            if(location.getRow() == nowLocation.getRow() && location.getColumn() == nowLocation.getColumn()) contain = true;
        }
        if(!contain) return false;
        int count = 0;
        for (ChessBoardLocation location : chessLocationList) {
            if (isInGoalDistrict(location, playerIndex))
                count++;
        }
        return (playerNum == 2 && count >= 15) || (playerNum == 4 && count >= 9);
    }
    private boolean isInGoalDistrict(ChessBoardLocation location, int playerIndex){
        ChessPiece piece = model.getChessPieceAt(location);
        return piece != null && piece.getColor().equals(chessColor[playerIndex - 1]);
    }

    //悔棋
    private Undo recordStep = new Undo();
    public void lastPlayer() {
        this.currentPlayerNum -= 1;
        if (currentPlayerNum < 0 ) setCurrentPlayerNum(this.playerNum-1);
        checkCurrentPlayer(getCurrentPlayerNum());
    }
    public void undoStep(){
        if (recordStep==null){
            throw new IllegalArgumentException("No Previous Step");
        }
        model.moveChessPiece(recordStep.getEnd(), recordStep.getStart());
        view.setChessAtGrid(recordStep.getStart(), recordStep.getColor());
        view.removeChessAtGrid(recordStep.getEnd());
        view.repaint();
        //
        lastPlayer();
        recordStep = recordStep.getLastStep();
        gameFrame.tf0.setText("Player"+ nowPlayer(false) );
    }

    //jump
    private ArrayList<ChessBoardLocation> validMoveList = new ArrayList<>();
    private boolean canMove(ChessBoardLocation dest){
        boolean check = false;
        for (ChessBoardLocation location : validMoveList) {
            if (dest.getRow() == location.getRow() && dest.getColumn() == location.getColumn())
                check = true;
        }
        return check;
    }
    private void updateList(ChessBoardLocation selected){
        validMoveList.clear();
        // add valid move
        addValidMove(selected);
        // add valid jump
        addValidJump(selected);
    }
    private void addValidMove(ChessBoardLocation selected){
        for ( int row = selected.getRow() -1; row <= selected.getRow() +1; row++ ){
            for ( int col = selected.getColumn() -1; col <= selected.getColumn() +1; col++ ){
                if ( 0 <= row && row < 19 && 0 <= col && col < 19 ){
                    if ( model.isValidMove(selected, model.getGridAt(row,col).getLocation() )
                            && campCheck(selected, model.getGridAt(row,col).getLocation() ) )
                        validMoveList.add(model.getGridAt(row,col).getLocation());
                }
            }
        }
    }
    private boolean campCheck(ChessBoardLocation src, ChessBoardLocation dest){
        return !isInGoalCamp(src) || isInGoalCamp(dest);
    }
    private boolean isInGoalCamp(ChessBoardLocation location){
        boolean check = false;
        int player = nowPlayer(false);
        int row = location.getRow();
        int col = location.getColumn();
        if ( playerNum == 2 ){
            if ( player == 1 ){
                if ( row >= 14 && col >= 14 ) check = true;
                if ( row + col <= 30 ) check = false;
            }
            else {
                if ( row <= 4 && col <= 4 ) check = true;
                if ( row + col >= 6 ) check = false;
            }
        }
        else {
            if ( player == 1 ){
                //1-2
                if ( row <= 3 && col >= 15 ) check = true;
                if ( row == 15 && col == 2 ) check = false;
                if ( row == 15 && col == 3 ) check = false;
                if ( row == 16 && col == 3 ) check = false;
            }
            else if ( player == 2 ) {
                //2-3
                if ( row >= 15 && col >= 15 ) check = true;
                if ( row + col <= 31 ) check = false;
            }
            else if ( player == 3 ){
                //3-4
                if ( row >= 15 && col <= 3) check = true;
                if ( row == 3 && col == 16 ) check = false;
                if ( row == 3 && col == 15 ) check = false;
                if ( row == 2 && col == 15 ) check = false;
            }
            else {
                //4-1
                if ( row <= 3 && col <= 3 ) check = true;
                if ( row + col >= 5 ) check = false;
            }
        }
        return check;
    }


    private void checkJumpDest(int row, int col, int r, int c){
        ChessBoardLocation src = new ChessBoardLocation(row,col);
        if (isInBoard(row+2*r,col+2*c)){
            ChessBoardLocation dest = new ChessBoardLocation(row+2*r,col+2*c);
            ChessBoardLocation middle = new ChessBoardLocation(row+r,col+c);
            if ( model.getChessPieceAt(dest) == null && model.getChessPieceAt(middle) != null ){
                if (!isLocationInList(dest) && recursionTimes < 1000) {
                    if (campCheck(src,dest)) {
                        validMoveList.add(dest);
                        addValidJump(dest);
                    }
                }
            }

        }
    }
    private int recursionTimes = 0;
    private void addValidJump(ChessBoardLocation selected){
        int row = selected.getRow();
        int col = selected.getColumn();
        recursionTimes++;

        checkJumpDest(row,col,-1,-1);
        checkJumpDest(row,col,-1,0);
        checkJumpDest(row,col,-1,1);
        checkJumpDest(row,col,0,-1);
        checkJumpDest(row,col,0,1);
        checkJumpDest(row,col,1,-1);
        checkJumpDest(row,col,1,0);
        checkJumpDest(row,col,1,1);

    }// end of method

    private boolean isInBoard(int row, int col){
        return ( 0 <= row && row < 19 && 0 <= col && col < 19 );
    }
    private boolean isLocationInList(ChessBoardLocation location){
        for (ChessBoardLocation chessBoardLocation : validMoveList) {
            if (location.getRow() == chessBoardLocation.getRow() && location.getColumn() == chessBoardLocation.getColumn())
                return true;
        }
        return false;
    }


    public void setCurrentPlayerNum(int currentPlayerNum) { this.currentPlayerNum = currentPlayerNum; }
    public int getCurrentPlayerNum() { return this.currentPlayerNum; }

    public void setCurrentPlayer(Color color) { this.currentPlayer = color; }

    public void setSelectedPiece(ChessPiece selectedPiece) { this.selectedPiece = selectedPiece; }
    public void setSelectedLocation(ChessBoardLocation selectedLocation) { this.selectedLocation = selectedLocation; }

    public void setBelong(int num) {
        int dimension = model.getDimension();

        if(num == 2){
            for(int i = 0; i < 5; i++) belong[0][i] = Color.RED;
            for(int i = 0; i < 5; i++) belong[1][i] = Color.RED;
            for(int i = 0; i < 4; i++) belong[2][i] = Color.RED;
            for(int i = 0; i < 3; i++) belong[3][i] = Color.RED;
            for(int i = 0; i < 2; i++) belong[4][i] = Color.RED;


            for(int i = dimension - 5; i < dimension; i++) belong[dimension - 1][i] = Color.GREEN;
            for(int i = dimension - 5; i < dimension; i++) belong[dimension - 2][i] = Color.GREEN;
            for(int i = dimension - 4; i < dimension; i++) belong[dimension - 3][i] = Color.GREEN;
            for(int i = dimension - 3; i < dimension; i++) belong[dimension - 4][i] = Color.GREEN;
            for(int i = dimension - 2; i < dimension; i++) belong[dimension - 5][i] = Color.GREEN;
        }
        else{
            for(int i = 0; i < 4; i++) belong[0][i] = Color.RED;
            for(int i = 0; i < 4; i++) belong[1][i] = Color.RED;
            for(int i = 0; i < 3; i++) belong[2][i] = Color.RED;
            for(int i = 0; i < 2; i++) belong[3][i] = Color.RED;

            for(int i = dimension - 4; i < dimension; i++) belong[0][i] = Color.WHITE;
            for(int i = dimension - 4; i < dimension; i++) belong[1][i] = Color.WHITE;
            for(int i = dimension - 3; i < dimension; i++) belong[2][i] = Color.WHITE;
            for(int i = dimension - 2; i < dimension; i++) belong[3][i] = Color.WHITE;

            for(int i = 0; i < 2; i++) belong[dimension - 4][i] = Color.GREEN;
            for(int i = 0; i < 3; i++) belong[dimension - 3][i] = Color.GREEN;
            for(int i = 0; i < 4; i++) belong[dimension - 2][i] = Color.GREEN;
            for(int i = 0; i < 4; i++) belong[dimension - 1][i] = Color.GREEN;

            for(int i = dimension - 4; i < dimension; i++) belong[dimension - 1][i] = Color.BLACK;
            for(int i = dimension - 4; i < dimension; i++) belong[dimension - 2][i] = Color.BLACK;
            for(int i = dimension - 3; i < dimension; i++) belong[dimension - 3][i] = Color.BLACK;
            for(int i = dimension - 2; i < dimension; i++) belong[dimension - 4][i] = Color.BLACK;
        }
    }


    // AI
    private void computerTurn(){
        ChessMove move = bestMovement();
        moveRecord.add(move); // 记录走棋
        locationRecord.add(move.getSrc());
        locationRecord.add(move.getDest());
        ChessBoardLocation src = move.getSrc();
        ChessBoardLocation dest = move.getDest();
        // move
        model.moveChessPiece(src, dest);
        view.setChessAtGrid(dest, Color.GREEN);
        view.removeChessAtGrid(src);
        view.repaint();
        // 悔棋记录
        if (isFirst) isFirst = false;
        else {
            Undo lastStep = new Undo(recordStep.getStart(),recordStep.getEnd(),recordStep.getColor());
            lastStep.setLastStep(recordStep.getLastStep());
            recordStep.setLastStep( lastStep );
        }
        recordStep.setStart(src);
        recordStep.setEnd(dest);
        recordStep.setColor(Color.GREEN);
        // 胜利警告判断
        if ( isAboutToWin(2, dest) && isFirstPlayerToWin ) {
            warning.audioClip.play();
            //System.out.printf("Player %d is about to win!", nowPlayer(false) );
            isFirstPlayerToWin = false;
        }
        // 判断胜利
        if(checkWin(dest, true)){
            Victory victory = new Victory(getGameFrame(), 1);
            victory.setVisible(true);
            gameFrame.setVisible(false);
        }
        // 显示回合信息
        gameFrame.tf0.setText("Player"+ nowPlayer(false) );
        // 结束回合
        nextPlayer(getCurrentPlayerNum(), false);
    }
    private ChessMove bestMovement(){
        ArrayList<ChessBoardLocation> chessList = new ArrayList<>();
        for(int row = 0; row < 19; row++){
            for(int col = 0; col < 19; col++){
                ChessBoardLocation location = new ChessBoardLocation(row, col);
                ChessPiece piece = model.getChessPieceAt(location);
                if(piece != null && piece.getColor().equals(Color.GREEN)){
                    chessList.add(location);
                }
            }
        }

        while(true){
            int num = (int) Math.round(Math.random()*18);
            ChessBoardLocation now = chessList.get(num);

            ArrayList<ChessBoardLocation> redList = model.getList(0);
            ArrayList<ChessBoardLocation> dest = destination(now);
            if(dest.isEmpty()) continue;

            boolean in = false;
            for(ChessBoardLocation location : redList){
                if(now.getRow() == location.getRow() && now.getColumn() == location.getColumn()) in = true;
            }

            ChessBoardLocation best = null;
            for(ChessBoardLocation location : dest){
                if(in){
                    boolean flag = false;
                    for(ChessBoardLocation temp : redList){
                        if(temp.getRow() == location.getRow() && temp.getColumn() == location.getColumn()) flag = true;
                    }
                    if(!flag) continue;
                }

                if(location.getRow() + location.getColumn() >= now.getRow() + now.getColumn()) continue;

                if(best == null){
                    best = location;
                }
                else if(location.getRow() + location.getColumn() < best.getRow() + best.getColumn()){
                    best = location;
                }
                else if(location.getRow() + location.getColumn() < best.getRow() + best.getColumn() && location.getRow()*location.getColumn() > best.getRow()*best.getColumn()){
                    best = location;
                }
            }
            if(best == null ) continue;

            ChessMove ans = new ChessMove();
            ans.setSrc(now);
            ans.setDest(best);

            return ans;
        }
    }


    private ArrayList<ChessMove> moveRecord = new ArrayList<>();
    private ArrayList<ChessBoardLocation> locationRecord = new ArrayList<>();


    // 音效
    private Music sound;
    public void startSound(String s){
        if (sound != null){
            sound.audioClip.stop();
        }
        sound = new Music(s);
        sound.audioClip.play();
    }
    private Music warning = new Music("sounds/warning.wav");



}// end of class
