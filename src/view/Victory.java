package view;


import controller.GameController;
import mainmenu.StartMenu;
import music.Music;
import view.GameFrame;
import mainmenu.Save;
import model.ChessBoard;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Victory extends JFrame{
    public Victory(GameFrame gameFrame, int currentPlayerNum){
        setTitle("Congratulations");
        setSize(600, 630);
        setLocationRelativeTo(null); // Center the window.
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setLayout(null);

        setLayout(new GridLayout(1, 1));
        JPanel panel = new JPanel(null);

        int winPlayer = currentPlayerNum + 1;
        if (winPlayer>gameFrame.getPlayerNum()) winPlayer = winPlayer % gameFrame.getPlayerNum();
        //
        String s = "images/v" + winPlayer + ".png";
        ImageIcon backGround = new ImageIcon(s);
        JLabel backLabel = new JLabel(backGround);
        backLabel.setSize(600,600);
        backLabel.setLocation(0,0);
        this.getLayeredPane().add(backLabel);

        Music music = new Music("sounds/win.wav");
        music.audioClip.play();


        JButton button = new JButton("返回主菜单");
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                SwingUtilities.invokeLater(() -> {
                    setVisible(false);
                    gameFrame.returnToMenu();
                });
            }
        });
        button.setLocation(50, 500);
        button.setSize(100, 30);

        button.setOpaque(false);
        panel.add(button);
        panel.setOpaque(false);
        add(panel);
    }
}
