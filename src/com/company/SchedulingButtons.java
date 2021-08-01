package com.company;
/**
 * This file has the class SchedulingButtons which holds the CREATE and From File button in our application
 * @author Nolen Johnson
 * @version 1.0 7/20/2021
 */
import javax.swing.*;
import java.time.format.DateTimeParseException;

/**
 * Represent the create and From File buttons in our calendar
 */
public class SchedulingButtons {
    private final JPanel panel;
    private final CalendarModel model;

    /**
     * Constructs SchedulingButtons object. Fills the panel with both buttons and adds action listeners to them.
     * @param modelParam Calendar model is passed
     */
    SchedulingButtons(CalendarModel modelParam){
        model = modelParam;
        panel = new JPanel();
        JButton createButton = new JButton("  Create   ");
        JButton fromFileButton = new JButton("From File");
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.add(createButton);
        panel.add(fromFileButton);
        createButton.addActionListener(e -> {
            Event newEvent;
            JTextField nameField = new JTextField();
            JTextField dateField = new JTextField();
            JTextField startTField = new JTextField();
            JTextField endTField = new JTextField();
            JPanel popupPanel = new JPanel();
            popupPanel.setLayout(new BoxLayout(popupPanel, BoxLayout.Y_AXIS));
            popupPanel.add(new JLabel("Please enter fields for your event: "));
            popupPanel.add(Box.createVerticalStrut(10));
            popupPanel.add(new JLabel("Name: "));
            popupPanel.add(nameField);
            popupPanel.add(new JLabel("Date: (MM/DD/YY)"));
            popupPanel.add(dateField);
            popupPanel.add(new JLabel("Start Time: (HH:MM)"));
            popupPanel.add(startTField);
            popupPanel.add(new JLabel("End Time: (HH:MM)"));
            popupPanel.add(endTField);
            boolean newEventConflicts;
            do {
                newEventConflicts = false;
                int userResponse = JOptionPane.showConfirmDialog(null, popupPanel, "New Event",
                        JOptionPane.CANCEL_OPTION);
                try {
                    if (userResponse == JOptionPane.OK_OPTION) {
                        newEvent = new Event(nameField.getText(), dateField.getText(), startTField.getText(),
                                endTField.getText());
                        for (Event event : model.getEvents()) {
                            if (event.conflicts(newEvent)) {
                                newEventConflicts = true;
                                JOptionPane.showMessageDialog(panel, "Error: cannot add " + newEvent.getName() +
                                        " due to a time conflict with " + event.getName() + ".\nEither enter a new " +
                                        "date/time or click cancel on the next panel.",
                                        "Error (Event not Added)", JOptionPane.ERROR_MESSAGE);
                                break;
                            }
                        }
                        if (!newEventConflicts) {
                            model.addEvent(newEvent);
                            model.setEventsToView();
                        }
                    }
                }
                catch (DateTimeParseException dateTimeParseException) {
                    JPanel panel = new JPanel();
                    JOptionPane.showMessageDialog(panel, "Error: invalid input. Please follow specified " +
                                    "format.", "Error (Event not Added)", JOptionPane.ERROR_MESSAGE);
                }
            } while (newEventConflicts);
        });
        fromFileButton.addActionListener(e-> {
            JTextField fileNameField = new JTextField();
            JPanel popupPanel = new JPanel();
            popupPanel.setLayout(new BoxLayout(popupPanel, BoxLayout.Y_AXIS));
            popupPanel.add(new JLabel("Please enter the name of the file to load recurring events from."));
            popupPanel.add(Box.createVerticalStrut(20));
            popupPanel.add(new JLabel("File Name (format it filename.txt):"));
            popupPanel.add(fileNameField);
            int userResponse = JOptionPane.showConfirmDialog(null, popupPanel,
                    "Loading Events From File", JOptionPane.DEFAULT_OPTION);
            String filename;
            if (userResponse == JOptionPane.OK_OPTION) {
                filename = fileNameField.getText();
                model.loadFile(filename);
                model.setEventsToView();
            }
        });
    }

    /**
     * Gets the modified panel. Used in CalendarView
     * @return returns panel
     */
    public JPanel getPanel(){
        return panel;
    }
}
