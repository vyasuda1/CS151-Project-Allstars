package com.company;
/**
 * This file contains a class that represents the view-changing buttons in our calendar program.
 * @author Haider Almandeel, Nolen Johnson, Viola Yasuda
 * @version 1.0 7/20/2021
 */

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Represents the view-changing buttons in our calendar program.
 */
public class ViewChangerButtons {
    private JPanel panel;
    private CalendarModel model;

    /**
     * Constructs a ViewChangerButtons object.
     * @param m the model to interact with
     * @author Haider Almandeel, Nolen Johnson, Viola Yasuda
     */
    public ViewChangerButtons(CalendarModel m) {
        model = m;
        panel = new JPanel();
        JButton dayButton = new JButton("Day");
        JButton weekButton = new JButton("Week");
        JButton monthButton = new JButton("Month");
        JButton agendaButton = new JButton("Agenda");

        dayButton.addActionListener(new ActionListener() {
            /**
             * Changes the view type to Day view when the Day button is clicked.
             * @param e the event in which the Day button is clicked
             * @author Haider Almandeel, Nolen Johnson, Viola Yasuda
             */
            @Override
            public void actionPerformed(ActionEvent e) {
                model.setViewType("Day");
            }
        });
        weekButton.addActionListener(new ActionListener() {
            /**
             * Changes the view type to Week view when the Week button is clicked.
             * @param e the event in which the Week button is clicked
             * @author Haider Almandeel, Nolen Johnson, Viola Yasuda
             */
            @Override
            public void actionPerformed(ActionEvent e) {
                model.setViewType("Week");
            }
        });
        monthButton.addActionListener(new ActionListener() {
            /**
             * Changes the view type to Month view when the Month button is clicked.
             * @param e the event in which the Month button is clicked
             * @author Haider Almandeel, Nolen Johnson, Viola Yasuda
             */
            @Override
            public void actionPerformed(ActionEvent e) {
                model.setViewType("Month");
            }
        });
        agendaButton.addActionListener(new ActionListener() {
            /**
             * Changes the view type to Agenda view when the Agenda button is clicked.
             * @param e the event in which the Agenda button is clicked
             * @author Haider Almandeel
             */
            @Override
            public void actionPerformed(ActionEvent e) {
                //JOptionPane objects to take start date and end date
                //startDate = [user input]
                //endDate = [user input]
                //model.setViewType(startDate, endDate);
            }
        });

        panel.add(dayButton);
        panel.add(weekButton);
        panel.add(monthButton);
        panel.add(agendaButton);
    }

    /**
     * Gets the panel that will be used in CalendarView.
     * @return panel
     * @author Haider Almandeel, Nolen Johnson, Viola Yasuda
     */
    public JPanel getPanel() {
        return panel;
    }
}
