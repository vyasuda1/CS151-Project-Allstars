package com.company;
/**
 * This file contains a class that represents the GUI of a calendar program.
 * @author Viola Yasuda
 * @version 1.1 7/23/2021
 */

import javax.swing.*;
import java.awt.*;

/**
 * Represents the GUI part of a calendar program.
 */
public class CalendarView {
    private final CalendarModel model;
    private final JTextArea textArea;
    public static final double ROW_1_WEIGHT = 0.1;
    public static final double ROW_2_WEIGHT = 0.1;
    public static final double ROW_3_WEIGHT = 0.8;
    public static final double ROW_4_WEIGHT = 0.1;
    public static final double COLUMN_WEIGHT = 0.5;

    /**
     * Constructs a CalendarView object.
     * @param model the model for the CalendarView to represent
     */
    public CalendarView(CalendarModel model) {
        this.model = model;
        model.registerCalendarView(this);
        textArea = new JTextArea(model.getEventsToView());
        textArea.setEditable(false);
    }

    /**
     * Displays the Calendar's GUI.
     */
    public void display() {
        //code for the text area
        JScrollPane scrollPane = new JScrollPane(textArea);

        //code for the buttons
        DateChangerButtons dateChangerButtons = new DateChangerButtons(model);
        JPanel dateButtons = dateChangerButtons.getPanel();
        ViewChangerButtons viewChangerButtons = new ViewChangerButtons(model);
        JPanel viewButtons = viewChangerButtons.getPanel();
        SchedulingButtons schedulingButtons = new SchedulingButtons(model);
        JPanel scheduleButtons = schedulingButtons.getPanel();
        CurrentCalendarComponent currentCalendarComponent = new CurrentCalendarComponent(model);
        JPanel currentCalendar = currentCalendarComponent.getPanel();

        JButton dateFirstFormatterButton = new JButton("Date First");
        JButton timeFirstFormetterButton = new JButton("Time FIrst");
        JPanel formatterPanel = new JPanel();
        formatterPanel.add(dateFirstFormatterButton);
        formatterPanel.add(timeFirstFormetterButton);

        //code for the frame
        JFrame frame = new JFrame();
        frame.setTitle("Allstars Calendar Program");
        frame.setSize(800, 600);
        frame.setResizable(false);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        frame.setLayout(new GridBagLayout());
        GridBagConstraints gridBagConstraints = new GridBagConstraints();

        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.weightx = COLUMN_WEIGHT;
        gridBagConstraints.weighty = ROW_1_WEIGHT;
        frame.add(dateButtons, gridBagConstraints);

        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.weightx = COLUMN_WEIGHT;
        gridBagConstraints.weighty = ROW_1_WEIGHT;
        frame.add(viewButtons, gridBagConstraints);

        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.weightx = COLUMN_WEIGHT;
        gridBagConstraints.weighty = ROW_2_WEIGHT;
        gridBagConstraints.fill = GridBagConstraints.VERTICAL;
        frame.add(scheduleButtons, gridBagConstraints);

        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridheight = 2;
        gridBagConstraints.weightx = COLUMN_WEIGHT;
        gridBagConstraints.weighty = 0.1;
        gridBagConstraints.fill = GridBagConstraints.BOTH;
        gridBagConstraints.insets = new Insets(0, 25, 0, 25);
        frame.add(scrollPane, gridBagConstraints);

        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.gridheight = 1;
        gridBagConstraints.weightx = COLUMN_WEIGHT;
        gridBagConstraints.weighty = ROW_3_WEIGHT;
        gridBagConstraints.insets = new Insets(0, 25, 25, 25);
        frame.add(currentCalendar, gridBagConstraints);

        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.weightx = COLUMN_WEIGHT;
        gridBagConstraints.weighty = ROW_4_WEIGHT;
        frame.add(formatterPanel, gridBagConstraints);
    }

    /**
     * Updates the CalendarView to show the correct list of events given the view type and date to view.
     * @param events the list of events to display in the text area
     */
    public void update(String events) {
        textArea.setText(events);
    }
}
