package com.company;
/**
 * This file contains a class that represents the view-changing buttons in our calendar program.
 * @author Haider Almandeel
 * @version 1.0 7/20/2021
 */
import javax.swing.*;

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

        dayButton.addActionListener(e -> model.setViewType("Day"));
        weekButton.addActionListener(e -> model.setViewType("Week"));
        monthButton.addActionListener(e -> model.setViewType("Month"));
        agendaButton.addActionListener(e -> {
            String startDate = JOptionPane.showInputDialog("Enter Start Date (MM/DD/YYYY) : ");
            String endDate = JOptionPane.showInputDialog("Enter End Date (MM/DD/YYYY): ");
            model.setViewType(startDate, endDate);
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
