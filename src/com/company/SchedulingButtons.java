import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.Scanner;

public class SchedulingButtons {
    private JPanel panel;
    private CalendarModel m;

    SchedulingButtons(CalendarModel model){
        m = model;
        panel = new JPanel();
        JButton createButton = new JButton("Create");
        JButton fromFileButton = new JButton("fromFile");
        panel.add(createButton);
        panel.add(fromFileButton);


        /**
         * action listener for createButton pressed
         * edit later on for time conflict
         */
        createButton.addActionListener(e -> {
            String name = JOptionPane.showInputDialog("Name: ");
            String date = JOptionPane.showInputDialog("Date: MM/DD/YYYY");
            String startT = JOptionPane.showInputDialog("Starting Time: HH") + ":00";
            String endT = JOptionPane.showInputDialog("Ending Time: HH") + ":00";
            Event E = new Event(name,date,startT,endT);
            m.addEvent(E);

                }
                );

        fromFileButton.addActionListener(e-> {
            String filename = JOptionPane.showInputDialog("File Name: ");
            m.loadFile(filename);

                }
                );

    }

    public JPanel getPanel(){
        return panel;
    }
}
