package com.company;
/**
 * File description here
 * @author Nolen Johnson
 * @version 1.0 7/20/2021
 */
import javax.swing.*;
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
            String filename = JOptionPane.showInputDialog("File Name: ");
            model.loadFile(filename);
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
