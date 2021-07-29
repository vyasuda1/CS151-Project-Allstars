package com.company;
/**
 * This file holds a class that represents the date-changing buttons in our calendar program.
 * @author Viola Yasuda
 * @verison 1.0 7/19/2021
 */

import javax.swing.*;
import java.awt.*;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
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
        todayButton.addActionListener(e -> model.setDateToView(LocalDate.now()));
        previousButton.addActionListener(e -> model.setPreviousDateToView());
        nextButton.addActionListener(e -> model.setNextDateToView());
        todayButton.addFocusListener(new FocusListener() {
            /**
             * Highlights button yellow if focus is gained.
             * @param e the event in which focus on button is gained
             */
            @Override
            public void focusGained(FocusEvent e) {
                todayButton.setBackground(Color.YELLOW);
            }

            /**
             * Sets button's background to default color if focus is lost.
             * @param e the event in which focus on button is lost
             */
            @Override
            public void focusLost(FocusEvent e) {
                todayButton.setBackground(null);
            }
        });
        previousButton.addFocusListener(new FocusListener() {
            /**
             * Highlights button yellow if focus is gained.
             * @param e the event in which focus on button is gained
             */
            @Override
            public void focusGained(FocusEvent e) {
                previousButton.setBackground(Color.YELLOW);
            }

            /**
             * Sets button's background to default color if focus is lost.
             * @param e the event in which focus on button is lost
             */
            @Override
            public void focusLost(FocusEvent e) {
                previousButton.setBackground(null);
            }
        });
        nextButton.addFocusListener(new FocusListener() {
            /**
             * Highlights button yellow if focus is gained.
             * @param e the event in which focus on button is gained
             */
            @Override
            public void focusGained(FocusEvent e) {
                nextButton.setBackground(Color.YELLOW);
            }

            /**
             * Sets button's background to default color if focus is lost.
             * @param e the event in which focus on button is lost
             */
            @Override
            public void focusLost(FocusEvent e) {
                nextButton.setBackground(null);
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
