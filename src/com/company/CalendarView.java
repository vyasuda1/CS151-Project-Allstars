package com.company;
/**
 * This file contains a class that represents the GUI of a calendar program.
 * @author Haider Almandeel, Nolen Johnson, Viola Yasuda
 * @version 1.0 7/17/2021
 */

import javax.swing.*;
import java.awt.*;
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
     * @author Viola Yasuda
     */
    public CalendarView(CalendarModel model) {
        frame = new JFrame();
        this.model = model;
        model.registerCalendarView(this);
        textArea = new JTextArea(model.getEventsToView());
        textArea.setEditable(false);
    }

    /**
     * Displays the Calendar's GUI. Add ViewChangerButtons, SchedulingButtons, and CurrentCalendarComponent.
     * @author Viola Yasuda
     */
    public void display() {
        //code for the text area
        JScrollPane scrollPane = new JScrollPane(textArea);

        //code for the buttons
        DateChangerButtons dateChangerButtons = new DateChangerButtons(model);
        JPanel dateButtons = dateChangerButtons.getPanel();

        ViewChangerButtons viewChangerButtons = new ViewChangerButtons(model);
        JPanel viewButtons = viewChangerButtons.getPanel();

        //code for the frame
        frame.setTitle("Allstars Calendar Program");
        frame.setSize(400, 400);
        frame.setResizable(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
        frame.add(scrollPane, BorderLayout.CENTER);
        frame.add(dateButtons, BorderLayout.NORTH);
        frame.add(viewButtons, BorderLayout.EAST);
    }

    /**
     * Updates the CalendarView to show the correct list of events given the view type and date to view.
     * @param events the list of events to display in the text area
     * @author Viola Yasuda
     */
    public void update(String events) {
        textArea.setText(events);
    }
}
