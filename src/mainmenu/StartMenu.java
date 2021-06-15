package mainmenu;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.FileNotFoundException;

public class StartMenu extends JFrame {
    private static final int FRAME_WIDTH = 285;
    private static final int FRAME_HEIGHT = 285;

    private Color boardColor1 = new Color(255, 255, 204);
    private Color boardColor2 = new Color(170, 170, 170);
    public void setBoardColor1(Color boardColor1) { this.boardColor1 = boardColor1; }
    public void setBoardColor2(Color boardColor2) { this.boardColor2 = boardColor2; }

    private JButton NewGame;
    private JButton Load;
    private JButton Settings;
    private JButton Inform;

    private NewGame newgame;
    private Load load;
    private Settings settings;
    private Inform inform;

    public StartMenu() {
        createComponents();

        setTitle("Halma");
        setSize(FRAME_WIDTH, FRAME_HEIGHT);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
    }

    // ActionListeners
    class NewGameListener implements ActionListener {
        public void actionPerformed(ActionEvent event) {
            setVisible(false);
            newgame = new NewGame();
            newgame.setBoardColor1(boardColor1);
            newgame.setBoardColor2(boardColor2);
            newgame.setVisible(true);
        }
    }

    class LoadListener implements ActionListener {
        public void actionPerformed(ActionEvent event) {
            setVisible(false);
            load = new Load(boardColor1,boardColor2);
            load.setVisible(true);
        }
    }

    class SettingsListener implements ActionListener {
        public void actionPerformed(ActionEvent event) {
            setVisible(false);
            settings = new Settings();
            settings.setVisible(true);
        }
    }

    class InformListener implements ActionListener {
        public void actionPerformed(ActionEvent event) {
            setVisible(false);
            inform = new Inform();
            inform.setVisible(true);
        }
    }

    // 组件初始化
    private void createComponents(){
        // 设置按钮样式
        NewGame = new JButton();
        Load = new JButton();
        Settings = new JButton();
        Inform = new JButton();
        // 边框
        NewGame.setBorderPainted(true);
        Load.setBorderPainted(true);
        Settings.setBorderPainted(true);
        Inform.setBorderPainted(true);
        // 贴图
        ImageIcon icon1 = new ImageIcon("images/newgame.png");
        ImageIcon icon2 = new ImageIcon("images/load.png");
        ImageIcon icon3 = new ImageIcon("images/settings.png");
        ImageIcon icon4 = new ImageIcon("images/inform.png");
        NewGame.setIcon(icon1);
        Load.setIcon(icon2);
        Settings.setIcon(icon3);
        Inform.setIcon(icon4);
        // 事件侦听器
        ActionListener listener1 = new StartMenu.NewGameListener();
        ActionListener listener2 = new StartMenu.LoadListener();
        ActionListener listener3 = new StartMenu.SettingsListener();
        ActionListener listener4 = new StartMenu.InformListener();
        NewGame.addActionListener( listener1 );
        Load.addActionListener( listener2 );
        Settings.addActionListener( listener3 );
        Inform.addActionListener( listener4 );
        // 按钮布局设置：1X4
        GridLayout buttons = new GridLayout(4,1);
        // 添加按钮
        JPanel panel = new JPanel(buttons);
        panel.add(NewGame);
        panel.add(Load);
        panel.add(Settings);
        panel.add(Inform);
        // 添加到面板
        add(panel);
    }
}// end of class
