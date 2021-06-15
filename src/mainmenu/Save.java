package mainmenu;

import com.sun.xml.internal.ws.api.addressing.OneWayFeature;
import controller.GameController;
import model.ChessBoard;
import mainmenu.SaveFile;
import mainmenu.NewGame;
import model.ChessBoardLocation;
import model.ChessPiece;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;

public class Save extends JFrame {
    private static final int FRAME_WIDTH = 500;
    private static final int FRAME_HEIGHT = 300;
    private JButton back;
    private JButton check;
    private GameController gameController;

    public Save(GameController controller){
        createComponents();
        setTitle("Save Halma");
        setSize(FRAME_WIDTH, FRAME_HEIGHT);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.gameController = controller;
    }

    private void createComponents(){
        Font cst20 = new Font("宋体",Font.BOLD,20);

        // button back to start menu
        back = new JButton("返回");
        back.setFont(cst20);
        ActionListener listener0 = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setVisible(false);
            }
        };
        back.addActionListener(listener0);


        // 保存
        JPanel savePanel = new JPanel();
        // 设置标签
        JLabel saveLabel = new JLabel("保存为");
        saveLabel.setFont(cst20);

        JRadioButton save1;
        JRadioButton save2;
        JRadioButton save3;

        save1 = new JRadioButton("savedgames1", true);
        save2 = new JRadioButton("savedgames2", false);
        save3 = new JRadioButton("savedgames3", false);
        save1.setFont(cst20);
        save2.setFont(cst20);
        save3.setFont(cst20);

        // 单选按钮分组
        ButtonGroup saveGroup = new ButtonGroup();
        saveGroup.add(save1);
        saveGroup.add(save2);
        saveGroup.add(save3);
        // 添加
        savePanel.add(saveLabel);
        savePanel.add(save1);
        savePanel.add(save2);
        savePanel.add(save3);

        Box vBox = Box.createVerticalBox();
        JPanel panel0 = new JPanel();
        panel0.add(back);
        vBox.add(panel0);
        setContentPane(vBox);
        pack();

        check = new JButton("确定");
        check.setFont(cst20);

        ActionListener listener1 = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    File file;
                    if(save1.isSelected()) file = new File("savedgames//savedgame1.txt");
                    else if(save2.isSelected()) file = new File("savedgames//savedgame2.txt");
                    else file = new File("savedgames//savedgame3.txt");

                    FileWriter fileWriter = new FileWriter(file.getAbsoluteFile());
                    BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);

                    bufferedWriter.write(gameController.getPlayerNum() + " ");
                    //bufferedWriter.write(" ");
                    bufferedWriter.write(gameController.getCurrentPlayerNum() + " ");
                    //bufferedWriter.write(" ");
                    for(int row = 0; row < gameController.getModel().getDimension(); row++){
                        for(int col = 0; col < gameController.getModel().getDimension(); col++){
                            int i;
                            ChessPiece piece = gameController.getModel().getChessPieceAt(new ChessBoardLocation(row, col));
                            Color color = piece == null ? null : piece.getColor();
                            if (Color.RED.equals(color)) {
                                i = 0;
                            } else if (Color.GREEN.equals(color)) {
                                i = 1;
                            } else if (Color.BLACK.equals(color)) {
                                i = 2;
                            } else if (Color.WHITE.equals(color)) {
                                i = 3;
                            } else {
                                i = 4;
                            }
                            bufferedWriter.write(i + " ");
                            //bufferedWriter.write(" ");
                        }
                    }
                    bufferedWriter.close();

                } catch (IOException ex) {
                    ex.printStackTrace();
                }
                setVisible(false);
            }
        };
        check.addActionListener(listener1);
        add(check);

        // 页面布局
        GridLayout layout = new GridLayout(1, 1);
        JPanel panel = new JPanel(layout);
        panel.add(savePanel);
        add(panel);

        ;
    }
}
