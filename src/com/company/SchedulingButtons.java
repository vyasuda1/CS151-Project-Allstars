package com.company;
/**
 * Description here
 * @author Nolen Johnson
 * @version 1.0 7/20/21
 */
import javax.swing.*;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

public class SchedulingButtons {
    private JPanel panel;
    private CalendarModel m;

    SchedulingButtons(CalendarModel model){
        m = model;
        panel = new JPanel();
        AtomicInteger option = new AtomicInteger();
        AtomicBoolean doesConflict = new AtomicBoolean(false);
        JButton createButton = new JButton("Create");
        JButton fromFileButton = new JButton("fromFile");
        panel.add(createButton);
        panel.add(fromFileButton);


        /**
         * action listener for createButton pressed
         */
        createButton.addActionListener(e -> {
            Event newEvent;
            do {
                String name = JOptionPane.showInputDialog("Name: ");
                String date = JOptionPane.showInputDialog("Date: MM/DD/YYYY");
                String startT = JOptionPane.showInputDialog("Starting Time: HH") + ":00";
                String endT = JOptionPane.showInputDialog("Ending Time: HH") + ":00";
                newEvent = new Event(name, date, startT, endT);
                for (Event event : m.getEvents()){
                    if (event.conflicts(newEvent)){
                        doesConflict.set(true);
                        option.set(JOptionPane.showConfirmDialog(null, "Would you like try to create another event", "New Event ?", JOptionPane.YES_NO_OPTION));

                    }
                }
            } while(option.get() == 1);
            if (!doesConflict.get()) {
                m.addEvent(newEvent);
                m.setEventsToView();
            }

        });

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
