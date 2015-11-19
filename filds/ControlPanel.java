package filds;

import filds.ActionField;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by panasyuk on 24.08.2015.
 */
public class ControlPanel extends JFrame {
    ActionField actionField;

    public ControlPanel() throws Exception{
        actionField = new ActionField();
        JFrame start = new JFrame("Start Game");
        start.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        start.setMinimumSize(new Dimension(800, 600));
        start.setLocation(200, 100);
        start.pack();
        start.setVisible(true);
        Button button = new Button("Agressor");

        Button button1 = new Button("Defender");


        JPanel startPanel = new JPanel();
        startPanel.add(button);
        startPanel.add(button1);
        start.getContentPane().
                add(startPanel);
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                startPlay("agressor");
            }
        });


    }

    public void startPlay(String name) {
        try {
//            actionField = new filds.ActionField();
            //actionField.runTheGame();
        } catch (Exception e) {

        }

    }
}
