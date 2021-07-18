package com.company;
/**
 * This file contains a class that represents the GUI of a calendar program.
 * @author Viola Yasuda
 * @version 1.0 7/17/2021
 */

import javax.swing.*;
import java.util.ArrayList;

/**
 * Represents the GUI part of a calendar program.
 */
public class CalendarView {
    private ArrayList<JPanel> panels;
    private JFrame frame;
    private CalendarModel model;
    private JTextArea textArea;

    /**
     * Constructs a CalendarView object.
     * @param model the model for the CalendarView to represent
     */
    public CalendarView(CalendarModel model) {
        panels = new ArrayList<>();
        frame = new JFrame();
        this.model = model;
        model.registerCalendarView(this);
        textArea = new JTextArea(model.getEventsToView());
    }

    /**
     * Adds a panel to the CalendarView.
     * @param panel the panel to add to the CalendarView
     */
    public void addPanel(JPanel panel) {
        panels.add(panel);
    }

    /**
     * Displays the Calendar's GUI.
     */
    public void display() {
        //code for the text area
        JScrollPane scrollPane = new JScrollPane(textArea);

        //code for the buttons


        //code for the frame
        frame.setTitle("Allstars Calendar Program");
        frame.setSize(400, 400);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
        frame.add(scrollPane);

        for (JPanel p : panels) {
            frame.add(p);
        }
    }

    /**
     * Updates the CalendarView to show the correct list of events given the view type and date to view.
     * @param events the list of events to display in the text area
     */
    public void update(String events) {
        textArea.setText(events);
    }
}