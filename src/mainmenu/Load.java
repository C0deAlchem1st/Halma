package mainmenu;

import controller.CheckError;
import controller.GameController;
import model.ChessBoard;
import model.ChessBoardLocation;
import model.ChessPiece;
import view.ChessBoardComponent;
import view.GameFrame;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.Calendar;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

public class Load extends JFrame implements ActionListener {
    private Color boardColor1 = new Color(255, 255, 204);
    private Color boardColor2 = new Color(170, 170, 170);

    JLabel l1 = new JLabel("加载");
    JTextField t1 = new JTextField(40);
    JButton b1 = new JButton("选择");
    JButton b2 = new JButton("确定");
    JButton b3 = new JButton("返回");
    JFileChooser j1 = new JFileChooser("savedgames");
    static File fileFlag = new File("");
    int playerNum;
    int currentPlayerNum;
    boolean isAiMode;
    int[][] pieces;

    public Load( Color color1, Color color2 ) {
        boardColor1 = color1;
        boardColor2 = color2;

        setTitle("读取进度");
        setBounds(200, 200, 600, 140);
        setLayout(new FlowLayout());
        add(l1);
        add(t1);
        add(b1);
        add(b2);
        //add(b3);
        b1.addActionListener(this);
        setResizable(false);
        setVisible(true);
        validate();

        pieces = new int[19][19];
    }

    public void createComponents(){
        ActionListener listener1 = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                boolean out = false;

                try {
                    FileReader fileReader = new FileReader(fileFlag.getAbsoluteFile());
                    BufferedReader bufferedReader = new BufferedReader(fileReader);

                    playerNum = bufferedReader.read() - '0';
                    bufferedReader.read();
                    isAiMode = (bufferedReader.read() - '0') == 1;
                    bufferedReader.read();
                    currentPlayerNum = bufferedReader.read() - '0';
                    bufferedReader.read();
                    for(int i = 0; i < 19; i++){
                        for(int k = 0; k < 19; k++){
                            pieces[i][k] = bufferedReader.read() - '0';
                            bufferedReader.read();
                        }
                    }
                    if(bufferedReader.read() != -1) out = true;
                    bufferedReader.close();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }

                ChessBoardComponent chessBoardComponent = new ChessBoardComponent(640, 19,boardColor1,boardColor2);

                ChessBoard chessBoard = new ChessBoard(19, playerNum, false);
                for(int row = 0; row < 19; row++){
                    for(int col = 0; col < 19; col++){
                        Color color;
                        switch (pieces[row][col]){
                            case 0 : color = Color.RED; break;
                            case 1 : color = Color.GREEN; break;
                            case 2 : color = Color.BLACK; break;
                            case 3 : color = Color.WHITE; break;
                            default: color = null;
                        }
                        if(color != null) chessBoard.setChessPieceAt(new ChessBoardLocation(row, col), new ChessPiece(color));
                    }
                }

                GameController controller = new GameController(chessBoardComponent, chessBoard, playerNum, currentPlayerNum);
                controller.setCurrentPlayerNum(currentPlayerNum);
                controller.setAIMode(isAiMode);

                GameFrame mainFrame = new GameFrame(controller);
                mainFrame.add(chessBoardComponent);
                mainFrame.setVisible(true);
                mainFrame.startMusic("sounds/2.wav");

                controller.setGameFrame(mainFrame);

                CheckError checkError = new CheckError(controller, out, mainFrame);
                checkError.work();

                setVisible(false);
            }
        };
        b2.addActionListener(listener1);

        b3.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setVisible(false);
            }
        });
    }

    public void actionPerformed(ActionEvent e) {
        try {
            if (e.getSource() == b1) {
                int n = j1.showOpenDialog(null);
                String filename = j1.getSelectedFile().toString();
                if (n == JFileChooser.APPROVE_OPTION) {
                    t1.setText(filename);
                    fileFlag = new File(filename);
                }
                createComponents();
            }
        } catch (Exception x) {
            System.out.println(x);
        }
    }

}