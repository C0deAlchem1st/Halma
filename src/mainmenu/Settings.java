package mainmenu;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Scanner;

public class Settings extends JFrame {
    private static final int FRAME_WIDTH = 300;
    private static final int FRAME_HEIGHT = 300;

    private Color boardColor1 = new Color(255, 255, 204);
    private Color boardColor2 = new Color(170, 170, 170);

    private JButton back;

    public Settings(){
        createComponents();
        setTitle("Halma Settings");
        setSize(FRAME_WIDTH, FRAME_HEIGHT);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        setLayout(null);
    }

    private void createComponents() {

        JButton color1 = new JButton("自定义棋盘颜色1");
        JButton color2 = new JButton("自定义棋盘颜色2");
        JButton defaultColor = new JButton("恢复默认设置");
        color1.setSize(200,30);
        color2.setSize(200,30);
        defaultColor.setSize(200,30);
        color1.setLocation(50,50);
        color2.setLocation(50,100);
        defaultColor.setLocation(50,150);
        color1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Color color = JColorChooser.showDialog(null,"选择颜色1",null);
                if (color != null) {
                    boardColor1 = color;
                }
            }
        });
        color2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Color color = JColorChooser.showDialog(null,"选择颜色2",null);
                if (color != null) {
                    boardColor2 = color;
                }
            }
        });

        // button back to start menu
        back = new JButton("返回");
        back.setSize(100, 30);
        ActionListener listener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                StartMenu frame = new StartMenu();
                frame.setBoardColor1(boardColor1);
                frame.setBoardColor2(boardColor2);
                frame.setVisible(true);
                setVisible(false);
            }
        };
        back.addActionListener(listener);
        back.setLocation(100,200);
        add(color1);
        add(color2);
        add(defaultColor);
        add(back);
    }

}// end of class
