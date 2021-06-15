package mainmenu;

import controller.GameController;
import model.ChessBoardLocation;
import model.ChessPiece;

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

public class SaveAs extends JFrame implements ActionListener {
    JLabel l1 = new JLabel("保存在");
    JTextField t1 = new JTextField(40);
    JButton b1 = new JButton("选择");
    JButton b2 = new JButton("保存");
    JFileChooser j1 = new JFileChooser("savedgames");
    static File fileFlag = new File("");
    GameController controller;

    public SaveAs(GameController controller) {
        setTitle("另存为");
        setBounds(200, 200, 600, 140);
        setLayout(new FlowLayout());
        add(l1);
        add(t1);
        add(b1);
        add(b2);
        b1.addActionListener(this);
        setResizable(false);
        setVisible(true);
        validate();

        this.controller = controller;
    }

    public void createComponents(){
        ActionListener listener1 = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {

                    FileWriter fileWriter = new FileWriter(fileFlag.getAbsoluteFile());
                    BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);

                    bufferedWriter.write(controller.getPlayerNum() + "\n");
                    bufferedWriter.write((controller.isAIMode() ? 1 : 0) + "\n");
                    bufferedWriter.write(controller.getCurrentPlayerNum() + "\n");
                    for(int row = 0; row < controller.getModel().getDimension(); row++){
                        for(int col = 0; col < controller.getModel().getDimension(); col++){
                            int i;
                            ChessPiece piece = controller.getModel().getChessPieceAt(new ChessBoardLocation(row, col));
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
                            bufferedWriter.write(i + (col == controller.getModel().getDimension() - 1 ? "\n" : " "));
                        }
                    }
                    bufferedWriter.close();

                } catch (IOException ex) {
                    ex.printStackTrace();
                }
                setVisible(false);
            }
        };
        b2.addActionListener(listener1);
    }

    public void actionPerformed(ActionEvent e) {
        try {
            if (e.getSource() == b1) {
                int n = j1.showOpenDialog(null);
                String filename = j1.getSelectedFile().toString();
                if (filename.indexOf(".") != -1) {
                    filename = filename.substring(0, filename.indexOf("."));
                }
                if (n == JFileChooser.APPROVE_OPTION) {
                    t1.setText(filename);
                    fileFlag = new File(filename);
                }
                if(!fileFlag.exists()) fileFlag.createNewFile();
                createComponents();
            }
        } catch (Exception x) {
            System.out.println(x);
        }
    }

    public String getDate() {
        Calendar rightNow = Calendar.getInstance();
        System.out.println(rightNow.toString());
        int year = rightNow.YEAR;
        int date = rightNow.DATE;
        int month = rightNow.MONTH + 1;
        String d = year + "年" + month + "月" + date + "日";
        return d;
    }
}