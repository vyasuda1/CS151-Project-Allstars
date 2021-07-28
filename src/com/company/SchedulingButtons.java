package com.company;
/**
 * File description here
 * @author Nolen Johnson
 * @version 1.0 7/20/2021
 */
import javax.swing.*;
import java.awt.*;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Class description here
 */
public class SchedulingButtons {
    private final JPanel panel;
    private final CalendarModel model;

    /**
     * Constructor description here
     * @param modelParam param description here
     */
    SchedulingButtons(CalendarModel modelParam){
        model = modelParam;
        panel = new JPanel();
        AtomicInteger option = new AtomicInteger();
        AtomicBoolean doesConflict = new AtomicBoolean(false);
        JButton createButton = new JButton(" Create  ");
        JButton fromFileButton = new JButton("fromFile");
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.add(createButton);
        panel.add(fromFileButton);
        createButton.addActionListener(e -> {
            Event newEvent;
            do {
                String name = JOptionPane.showInputDialog("Name: ");
                String date = JOptionPane.showInputDialog("Date: MM/DD/YYYY");
                String startT = JOptionPane.showInputDialog("Starting Time: HH") + ":00";
                String endT = JOptionPane.showInputDialog("Ending Time: HH") + ":00";
                newEvent = new Event(name, date, startT, endT);
                for (Event event : model.getEvents()){
                    if (event.conflicts(newEvent)){
                        doesConflict.set(true);
                        option.set(JOptionPane.showConfirmDialog(null, "Would you like try to " +
                                "create another event", "New Event ?", JOptionPane.YES_NO_OPTION));
                    }
                }
            } while(option.get() == 1);
            if (!doesConflict.get()) {
                model.addEvent(newEvent);
                model.setEventsToView();
            }
        });
        fromFileButton.addActionListener(e-> {
            JTextField fileNameField = new JTextField(10);
            JPanel popupPanel = new JPanel();
            popupPanel.setLayout(new BoxLayout(popupPanel, BoxLayout.Y_AXIS));
            popupPanel.add(new JLabel("Please enter the name of the file to load recurring events from."));
            popupPanel.add(Box.createVerticalStrut(20));
            popupPanel.add(new JLabel("File Name (format it filename.txt):"));
            popupPanel.add(fileNameField);
            int result = JOptionPane.showConfirmDialog(null, popupPanel,
                    "Loading Events From File", JOptionPane.CLOSED_OPTION);
            String filename;
            if (result == JOptionPane.OK_OPTION) {
                filename = fileNameField.getText();
                model.loadFile(filename);
            }
        });
    }

    /**
     * Method description here
     * @return return description here
     */
    public JPanel getPanel(){
        return panel;
    }
}
