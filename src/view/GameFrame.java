package view;


import controller.GameController;
import mainmenu.SaveAs;
import mainmenu.StartMenu;
import music.Music;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.filechooser.FileFilter;
import java.applet.Applet;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.net.MalformedURLException;
import java.util.Scanner;

public class GameFrame extends JFrame {
    private Music music;
    public JLabel tf0 = new JLabel();
    public JLabel tf1 = new JLabel();
    public JLabel tf2 = new JLabel();
    public JLabel tf3 = new JLabel();
    public JLabel tf4 = new JLabel();
    private JButton back;

    private int playerNum;
    public int getPlayerNum() { return playerNum; }

    public GameFrame(GameController controller) {

        this.playerNum = controller.getPlayerNum();

        setTitle("Halma");
        setSize(840, 685);
        setLocationRelativeTo(null); // Center the window.
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setLayout(null);
        setResizable(false);

        //菜单栏
        JMenuBar menuBar = new JMenuBar();
        menuBar.setSize(840,30);
        menuBar.setLocation(0,0);
        JMenu musicPlayer = new JMenu("默认背景音乐");
        JMenu myMusic = new JMenu("自由点歌");
        JMenu stopMusic = new JMenu("关闭音乐");
        JMenu moveGuide = new JMenu("行棋指示器");
        JMenu soundSet = new JMenu("音效");
        menuBar.add(musicPlayer);
        menuBar.add(myMusic);
        menuBar.add(stopMusic);
        menuBar.add(moveGuide);
        menuBar.add(soundSet);

        JMenuItem openSound = new JMenuItem("开启");
        JMenuItem closeSound = new JMenuItem("关闭");
        soundSet.add(openSound);
        soundSet.add(closeSound);
        openSound.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                controller.setSoundOn(true);
            }
        });
        closeSound.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                controller.setSoundOn(false);
            }
        });

        JMenuItem openGuide = new JMenuItem("开启");
        JMenuItem closeGuide = new JMenuItem("关闭");
        JMenuItem choseGuideColor = new JMenuItem("自定义颜色");
        moveGuide.add(openGuide);
        moveGuide.add(closeGuide);
        moveGuide.add(choseGuideColor);

        openGuide.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                controller.setMoveGuideOn(true);
                controller.showGuide();
            }
        });
        closeGuide.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                controller.deGuide();
                controller.setMoveGuideOn(false);
            }
        });
        choseGuideColor.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (controller.isMoveGuideOn()) controller.setMoveGuideOn(false);
                Color color = JColorChooser.showDialog(null,"选择颜色",null);
                if (color != null) {
                    controller.setViewGuideColor(color);
                    controller.setMoveGuideOn(true);
                }
            }
        });

        JMenuItem stop = new JMenuItem("关闭");
        stopMusic.add(stop);
        JMenuItem music1 = new JMenuItem("BGM1");
        JMenuItem music2 = new JMenuItem("BGM2");
        JMenuItem music3 = new JMenuItem("BGM3");

        JMenuItem chooseMusic = new JMenuItem("在文件夹中选择");
        chooseMusic.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                MusicChooser mc = new MusicChooser();
                mc.setCurrentDirectory(new File("mymusic"));
                mc.chooseFileCheck = mc.showOpenDialog(null);
                if (mc.chooseFileCheck == JFileChooser.APPROVE_OPTION){
                    File file = mc.getSelectedFile();
                    startMusic( file.getPath() );
                }
            }
        });

        musicPlayer.add(music1);
        musicPlayer.add(music2);
        musicPlayer.add(music3);
        myMusic.add(chooseMusic);
        stop.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (music!=null) music.audioClip.stop();
            }
        });
        music1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                startMusic("sounds/1.wav");
            }
        });
        music2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                startMusic("sounds/2.wav");
            }
        });
        music3.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                startMusic("sounds/3.wav");
            }
        });

        add(menuBar);

        //信息栏
        JPanel panel0 = new JPanel();
        ImageIcon turn = new ImageIcon("images/turn.png");
        JLabel label0 = new JLabel(turn);
        panel0.add(label0);
        panel0.setSize(200,90);
        panel0.setLocation(630,30);
        add(panel0);

        //玩家
        JPanel panel1 = new JPanel();
        JPanel panel2 = new JPanel();
        JPanel panel3 = new JPanel();
        JPanel panel4 = new JPanel();
        ImageIcon player1 = new ImageIcon("images/player1.png");
        ImageIcon player2 = new ImageIcon("images/player2.png");
        ImageIcon player3 = new ImageIcon("images/player3.png");
        ImageIcon player4 = new ImageIcon("images/player4.png");
        JLabel label1 = new JLabel(player1);
        JLabel label2 = new JLabel(player2);
        JLabel label3 = new JLabel(player3);
        JLabel label4 = new JLabel(player4);
        panel1.add(label1);
        panel2.add(label2);
        panel3.add(label3);
        panel4.add(label4);
        panel1.setSize(200,90);
        panel2.setSize(200,90);
        panel3.setSize(200,90);
        panel4.setSize(200,90);
        panel1.setLocation(630,120);
        panel2.setLocation(630,210);
        panel3.setLocation(630,300);
        panel4.setLocation(630,390);
        add(panel1);
        add(panel2);
        add(panel3);
        add(panel4);

        //面板设置
        getLayeredPane().add(panel0,new Integer(Integer.MIN_VALUE));
        getLayeredPane().add(panel1,new Integer(Integer.MIN_VALUE));
        getLayeredPane().add(panel2,new Integer(Integer.MIN_VALUE));
        getLayeredPane().add(panel3,new Integer(Integer.MIN_VALUE));
        getLayeredPane().add(panel4,new Integer(Integer.MIN_VALUE));
        ((JPanel)this.getContentPane()).setOpaque(false);

        //信息文本框
        JPanel panel0t = new JPanel();
        JPanel panel1t = new JPanel();
        JPanel panel2t = new JPanel();
        JPanel panel3t = new JPanel();
        JPanel panel4t = new JPanel();
        Font cst20 = new Font("Calibri",Font.BOLD,20);
        tf0.setFont(cst20);
        tf1.setFont(cst20);
        tf2.setFont(cst20);
        tf3.setFont(cst20);
        tf4.setFont(cst20);
        //填充信息
        tf0.setText("Player"+controller.nowPlayer(false));
        if ( controller.getPlayerNum() == 2 ){
            if ( controller.nowPlayer(false) == 1 ){ tf1.setText("Ready"); tf2.setText("Waiting"); }
            else { tf2.setText("Ready"); tf1.setText("Waiting"); }
            tf3.setText("No Player"); tf4.setText("No Player");
        }
        else {
            if ( controller.nowPlayer(false) == 1 ){ tf1.setText("Ready"); tf2.setText("Waiting"); tf3.setText("Waiting"); tf4.setText("Waiting"); }
            else if ( controller.nowPlayer(false) == 2 ){ tf2.setText("Ready"); tf1.setText("Waiting"); tf3.setText("Waiting"); tf4.setText("Waiting"); }
            else if ( controller.nowPlayer(false) == 3 ){ tf3.setText("Ready"); tf2.setText("Waiting"); tf1.setText("Waiting"); tf4.setText("Waiting"); }
            else { tf4.setText("Ready"); tf2.setText("Waiting"); tf3.setText("Waiting"); tf1.setText("Waiting"); }
        }
        //面板
        panel0t.add(tf0);
        panel1t.add(tf1);
        panel2t.add(tf2);
        panel3t.add(tf3);
        panel4t.add(tf4);
        final int tfWidth = 140;
        final int tfHeight = 26;
        final int tfx = 670;
        final int tfy0 = 85;
        panel0t.setSize(tfWidth,tfHeight);
        panel1t.setSize(tfWidth,tfHeight);
        panel2t.setSize(tfWidth,tfHeight);
        panel3t.setSize(tfWidth,tfHeight);
        panel4t.setSize(tfWidth,tfHeight);
        panel0t.setLocation(tfx,tfy0);
        panel1t.setLocation(tfx,tfy0+90);
        panel2t.setLocation(tfx,tfy0+180);
        panel3t.setLocation(tfx,tfy0+270);
        panel4t.setLocation(tfx,tfy0+360);
        add(panel0t);
        add(panel1t);
        add(panel2t);
        add(panel3t);
        add(panel4t);


        //存档按钮
        JButton save = new JButton();
        ImageIcon icon1 = new ImageIcon("images/save.png");
        save.setIcon(icon1);
        save.setSize(200,45);
        save.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                SaveAs saveAs = new SaveAs(controller);
            }
        });
        save.setLocation(630,475);
        add(save);

        //PASS按钮
        JButton pass = new JButton();
        ImageIcon icon2 = new ImageIcon("images/pass.png");
        pass.setIcon(icon2);
        pass.setSize(200,45);
        pass.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                controller.nextPlayer(controller.getCurrentPlayerNum(), controller.isAIMode());
                tf0.setText("Player"+ (controller.nowPlayer(false)) );
                controller.deChosenMark();
                //音效
                if (controller.isSoundOn()) controller.startSound("sounds/pass.wav");
            }
        });
        pass.setLocation(630,520);
        add(pass);

        //悔棋按钮
        JButton regret = new JButton();
        ImageIcon icon3 = new ImageIcon("images/regret.png");
        regret.setIcon(icon3);
        regret.setSize(200,45);
        regret.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                controller.undoStep();
                //音效
                if (controller.isSoundOn()) controller.startSound("sounds/undo.wav");
            }
        });
        regret.setLocation(630,565);
        add(regret);

        //返回主菜单按钮
        back = new JButton();
        ImageIcon icon4 = new ImageIcon("images/back.png");
        back.setIcon(icon4);
        back.setSize(200,45);
        back.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                music.audioClip.stop();
                setVisible(false);
                JFrame frame = new StartMenu();
                frame.setVisible(true);
            }
        });
        back.setLocation(630,610);
        add(back);


    }// end of constructor


    public void startMusic(String s){
        if (music != null) {         //如果有音频在播放
            music.audioClip.stop();  //停止当前音频的播放
        }
        music = new Music(s);
        music.audioClip.play();
    }

    public void returnToMenu(){
        back.doClick();
    }

}// end of class
