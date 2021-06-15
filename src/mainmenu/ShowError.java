package mainmenu;

import listener.Listener;
import view.GameFrame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ShowError extends JFrame {
    JLabel errorMessage1 = new JLabel();
    JLabel errorMessage2 = new JLabel();
    JLabel errorMessage3 = new JLabel();
    JLabel errorMessage4 = new JLabel();
    JButton returnMenu = new JButton();
    GameFrame gameFrame;
    int x, y;

    public ShowError(String error1, String error2, String error3, String error4, GameFrame gameFrame){
        this.x = 100;
        this.y = 100;
        Font cst20 = new Font("宋体",Font.BOLD,20);
        setTitle("读取错误");
        setSize(500, 500);
        setLocationRelativeTo(null);
        setLayout(null);

        if(!error1.equals("")){
            errorMessage1.setText(error1);
            errorMessage1.setFont(cst20);
            errorMessage1.setSize(300, 50);
            errorMessage1.setLocation(this.x, this.y);
            this.y += 50;
            add(errorMessage1);
        }
        if(!error2.equals("")){
            errorMessage2.setText(error2);
            errorMessage2.setFont(cst20);
            errorMessage2.setSize(300, 50);
            errorMessage2.setLocation(this.x, this.y);
            this.y += 50;
            add(errorMessage2);
        }
        if(!error3.equals("")){
            errorMessage3.setText(error3);
            errorMessage3.setFont(cst20);
            errorMessage3.setSize(300, 50);
            errorMessage3.setLocation(this.x, this.y);
            this.y += 50;
            add(errorMessage3);
        }
        if(!error4.equals("")){
            errorMessage4.setText(error4);
            errorMessage4.setFont(cst20);
            errorMessage4.setSize(300, 50);
            errorMessage4.setLocation(this.x, this.y);
            this.y += 50;
            add(errorMessage4);
        }

        returnMenu.setText("返回");
        returnMenu.setFont(cst20);
        returnMenu.setBounds(360, 360, 80, 60);
        add(returnMenu);
        this.gameFrame = gameFrame;
        setVisible(true);
    }

    public void work(){
        ActionListener listener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setVisible(false);
                getGameFrame().returnToMenu();
                //getGameFrame().setVisible(false);
            }
        };
        returnMenu.addActionListener(listener);
    }

    GameFrame getGameFrame() { return this.gameFrame; }
}
