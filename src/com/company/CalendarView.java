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

        //code for the frame
        JFrame frame = new JFrame();
        frame.setTitle("Allstars Calendar Program");
        frame.setSize(900, 600);
        frame.setResizable(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
        frame.add(scrollPane, BorderLayout.CENTER);
        frame.add(dateButtons, BorderLayout.NORTH);
        frame.add(viewButtons, BorderLayout.SOUTH);
        frame.add(scheduleButtons, BorderLayout.EAST);
        frame.add(currentCalendar, BorderLayout.WEST);
    }

    /**
     * Updates the CalendarView to show the correct list of events given the view type and date to view.
     * @param events the list of events to display in the text area
     */
    public void update(String events) {
        textArea.setText(events);
    }
}
