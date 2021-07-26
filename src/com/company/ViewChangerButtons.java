package com.company;
/**
 * This file contains a class that represents the view-changing buttons in our calendar program.
 * @author Haider Almandeel
 * @version 1.0 7/20/2021
 */
import javax.swing.*;
import java.awt.*;

/**
 * Represents the view-changing buttons in our calendar program.
 */
public class ViewChangerButtons {
    private final JPanel panel;
    private final CalendarModel model;

    /**
     * Constructs a ViewChangerButtons object.
     * @param modelParam the model to interact with
     */
    public ViewChangerButtons(CalendarModel modelParam) {
        model = modelParam;
        panel = new JPanel();
        JButton dayButton = new JButton("Day");
        JButton weekButton = new JButton("Week");
        JButton monthButton = new JButton("Month");
        JButton agendaButton = new JButton("Agenda");

        dayButton.addActionListener(e -> {
            model.setViewType("Day");
            dayButton.setBackground(Color.YELLOW);
            weekButton.setBackground(null);
            monthButton.setBackground(null);
            agendaButton.setBackground(null);
        });
        weekButton.addActionListener(e -> {
            model.setViewType("Week");
            dayButton.setBackground(null);
            weekButton.setBackground(Color.YELLOW);
            monthButton.setBackground(null);
            agendaButton.setBackground(null);
        });
        monthButton.addActionListener(e -> {
            model.setViewType("Month");
            dayButton.setBackground(null);
            weekButton.setBackground(null);
            monthButton.setBackground(Color.YELLOW);
            agendaButton.setBackground(null);
        });
        agendaButton.addActionListener(e -> {
            String startDate = JOptionPane.showInputDialog("Enter Start Date (MM/DD/YYYY) : ");
            String endDate = JOptionPane.showInputDialog("Enter End Date (MM/DD/YYYY): ");
            model.setViewType(startDate, endDate);
            dayButton.setBackground(null);
            weekButton.setBackground(null);
            monthButton.setBackground(null);
            agendaButton.setBackground(Color.YELLOW);
        });

        panel.add(dayButton);
        panel.add(weekButton);
        panel.add(monthButton);
        panel.add(agendaButton);
    }

    /**
     * Gets the panel that will be used in CalendarView.
     * @return panel
     */
    public JPanel getPanel() {
        return panel;
    }
}
