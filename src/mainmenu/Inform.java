package mainmenu;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Inform extends JFrame {
    private static final int FRAME_WIDTH = 1100;
    private static final int FRAME_HEIGHT = 700;
    private JTextArea resultArea;
    private JButton back;

    public Inform() {
        resultArea = new JTextArea();
        createComponents();
        setTitle("Halma Information");
        setSize(FRAME_WIDTH, FRAME_HEIGHT);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    private void createComponents() {
        // button back to start menu
        back = new JButton("返回");
        ActionListener listener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setVisible(false);
                JFrame frame = new StartMenu();
                frame.setVisible(true);
            }
        };
        back.addActionListener(listener);
        Font cst20 = new Font("宋体",Font.BOLD,20);
        back.setFont(cst20);

        // text information
        String text =
                "Halma跳棋规则：\n\n"+
                "一、棋盘元素\n" +
                "1.棋盘：16×16方格。\n" +
                "2.营地：游戏开始时玩家的棋子所在地。二人对战中营地边界以青色线标出，共19格；\n" +
                        "    四人对战中营地边界以红色线标出，共13格。当四个玩家分成两组时，队友的营地互为对角。\n" +
                "3.棋子：游戏开始时，玩家的棋子填满自家的营地。每个玩家的棋子颜色不同，以示区分。\n" +
                "\n" +
                "二、胜利目标\n" +
                "1.个人对战中，最先将自己的所有棋子移动到对角处的目标营地内的玩家胜利。\n" +
                "2.团队对战中，最先将全员的所有棋子移动到目标营地内的团队胜利。\n" +
                "\n" +
                "三、行动规则\n" +
                "1.游戏开始时，随机决定从哪一名玩家开始他的回合。\n" +
                "2.在一个玩家的回合内，他只能移动一个自己的棋子。\n" +
                "（1）棋子可以直接移动到相邻8个格子（垂直方向的4个和对角方向的4个）中未被其他棋子占据的地方。\n" +
                        "    若如此做，当前回合结束。\n" +
                "（2）若棋子的某个相邻格内有其他棋子占据，且沿此方向的第二格为空，\n" +
                        "    则棋子可以“跳过”占据其相邻格的棋子移动到该空位。若移动后仍有“跳”的合理位置，\n" +
                        "    则可以重复这一操作，直到玩家主动结束当前回合。\n" +
                "3.一个玩家结束他的当前回合后，顺时针方向的下一名玩家开始一个新的回合。\n" +
                "4.当玩家的任何棋子进入其目标营地后，该棋子的任何移动不能使其离开此营地。";

        // set panel
        JPanel panel1 = new JPanel();
        panel1.add(back);

        JPanel panel2 = new JPanel();
        panel2.setLayout( new BorderLayout() );
        panel2.add( panel1, BorderLayout.NORTH );

        JScrollPane scrollPane = new JScrollPane(resultArea);
        resultArea.append(text);
        Font st20 = new Font("宋体",Font.PLAIN,20);
        resultArea.setFont(st20);
        panel2.add( scrollPane, BorderLayout.CENTER );

        add(panel2);
    }

}// end of class
