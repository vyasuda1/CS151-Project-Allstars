package com.company;
/**
 *
 */

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 *
 */
public class ViewChangerButtons {
    private JPanel panel;
    private CalendarModel model;

    /**
     *
     * @param m
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
             *
             * @param e
             */
            @Override
            public void actionPerformed(ActionEvent e) {
                model.setViewType("Day");
            }
        });
        weekButton.addActionListener(new ActionListener() {
            /**
             *
             * @param e
             */
            @Override
            public void actionPerformed(ActionEvent e) {
                model.setViewType("Week");
            }
        });
        monthButton.addActionListener(new ActionListener() {
            /**
             *
             * @param e
             */
            @Override
            public void actionPerformed(ActionEvent e) {
                model.setViewType("Month");
            }
        });
        agendaButton.addActionListener(new ActionListener() {
            /**
             *
             * @param e
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
     *
     * @return
     */
    public JPanel getPanel() {
        return panel;
    }
}
