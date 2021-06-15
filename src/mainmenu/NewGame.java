package mainmenu;

import controller.GameController;
import model.ChessBoard;
import view.ChessBoardComponent;
import view.GameFrame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class NewGame extends JFrame {
    private static final int FRAME_WIDTH = 300;
    private static final int FRAME_HEIGHT = 300;

    private Color boardColor1 = new Color(255, 255, 204);
    private Color boardColor2 = new Color(170, 170, 170);
    public void setBoardColor1(Color boardColor1) { this.boardColor1 = boardColor1; }
    public void setBoardColor2(Color boardColor2) { this.boardColor2 = boardColor2; }

    JToggleButton randomFirst;
    // 通过isSelected判断按钮状态
    JRadioButton num2;
    JRadioButton num4;


    public NewGame(){
        createComponents();
        setTitle("Halma NewGame");
        setSize(FRAME_WIDTH, FRAME_HEIGHT);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    // 加载组件
    private void createComponents(){

        Font cst20 = new Font("宋体",Font.BOLD,20);
        // 第二行：人数选择
        JPanel numPanel = new JPanel();
        // 设置标签
        JLabel numLabel = new JLabel("玩家数目");
        numLabel.setFont(cst20);
        // 设置按钮
        num2 = new JRadioButton("2",true);
        num4 = new JRadioButton("4",false);
        num2.setFont(cst20);
        num4.setFont(cst20);
        // 单选按钮分组
        ButtonGroup numGroup = new ButtonGroup();
        numGroup.add(num2);
        numGroup.add(num4);
        // 添加
        numPanel.add(numLabel);
        numPanel.add(num2);
        numPanel.add(num4);

        // 第三行：返回/开始按钮
        JPanel buttonPanel = new JPanel();
        // 启用随机先手
        randomFirst = new JToggleButton("随机先手");
        randomFirst.setFont(cst20);
        // 开始按钮
        JButton start = new JButton("开始游戏");
        start.setFont(cst20);
        start.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                SwingUtilities.invokeLater(() -> {
                    ChessBoardComponent chessBoardComponent = new ChessBoardComponent(640, 19, boardColor1,boardColor2);
                    ChessBoard chessBoard = new ChessBoard(19, num2.isSelected() ? 2 : 4, true);
                    int playerNum = num2.isSelected() ? 2 : 4;
                    GameController controller = new GameController(chessBoardComponent, chessBoard,
                            num2.isSelected() ? 2 : 4, randomFirst.isSelected() ? (int)(playerNum*Math.random()) :0 );

                    GameFrame mainFrame = new GameFrame(controller);
                    mainFrame.add(chessBoardComponent);
                    mainFrame.setVisible(true);

                    mainFrame.startMusic("sounds/3.wav");

                    controller.setGameFrame(mainFrame);
                    setVisible(false);
                });
            }
        });
        // 人机模式
        JButton vsAI = new JButton("人机");
        vsAI.setFont(cst20);
        vsAI.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                SwingUtilities.invokeLater(() -> {
                    ChessBoardComponent chessBoardComponent = new ChessBoardComponent(640, 19,boardColor1,boardColor2);
                    ChessBoard chessBoard = new ChessBoard(19, 2, true);
                    GameController controller = new GameController(chessBoardComponent, chessBoard, 2,0);

                    GameFrame mainFrame = new GameFrame(controller);
                    mainFrame.add(chessBoardComponent);
                    mainFrame.setVisible(true);

                    mainFrame.startMusic("sounds/1.wav");
                    controller.setGameFrame(mainFrame);
                    // AI mode
                    controller.setAIMode(true);
                    mainFrame.tf2.setText("Computer");
                    setVisible(false);
                });
            }
        });
        // 返回按钮
        JButton back = new JButton("返回");
        back.setFont(cst20);
        back.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setVisible(false);
                JFrame frame = new StartMenu();
                frame.setVisible(true);
            }
        });
        // 加载
        buttonPanel.add(back);
        buttonPanel.add(start);
        buttonPanel.add(vsAI);
        buttonPanel.add(randomFirst);

        // 页面布局
        GridLayout layout = new GridLayout(2, 1);
        JPanel panel = new JPanel(layout);
        panel.add(numPanel);
        panel.add(buttonPanel);
        add(panel);
    }

}// end of class
