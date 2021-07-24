package com.company;
/**
 * This file holds a class that represents the date-changing buttons in our calendar program.
 * @author Viola Yasuda
 * @verison 1.0 7/19/2021
 */

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;

/**
 * Represents the part of the calendar program with the date-changing buttons.
 */
public class DateChangerButtons {
    private final CalendarModel model;
    private final JPanel panel;

    /**
     * Constructs a DateChangerButtons object. Fills the panel with the buttons and assigns actionListeners to them.
     * @param modelParam the model to interact with
     */
    public DateChangerButtons(CalendarModel modelParam) {
        model = modelParam;
        panel = new JPanel();
        JButton todayButton = new JButton("Today");
        JButton previousButton = new JButton("<");
        JButton nextButton = new JButton(">");
        todayButton.addActionListener(new ActionListener() {
            /**
             * Sets dateToView to today when Today button is clicked.
             * @param e the event in which the Today button is clicked
             */
            @Override
            public void actionPerformed(ActionEvent e) {
                model.setDateToView(LocalDate.now());
                System.out.println(model.getEventsToView()); //remove before final submission
            }
        });
        previousButton.addActionListener(new ActionListener() {
            /**
             * Sets dateToView to one day/week/month before current dateToView when Previous button is clicked.
             * @param e the event in which the Previous button is clicked
             */
            @Override
            public void actionPerformed(ActionEvent e) {
                model.setPreviousDateToView();
                System.out.println(model.getEventsToView()); //remove before final submission
            }
        });
        nextButton.addActionListener(new ActionListener() {
            /**
             * Sets dateToView to one day/week/month after current dateToView when Next button is clicked.
             * @param e the event in which the Next button is clicked
             */
            @Override
            public void actionPerformed(ActionEvent e) {
                model.setNextDateToView();
                System.out.println(model.getEventsToView()); //remove before final submission
            }
        });
        panel.add(todayButton);
        panel.add(previousButton);
        panel.add(nextButton);
    }

    /**
     * Gets the panel with the buttons. Used in CalendarView to add to the frame.
     * @return panel
     */
    public JPanel getPanel() {
        return panel;
    }
}
