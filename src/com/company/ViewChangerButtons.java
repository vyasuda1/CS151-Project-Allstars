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
            JTextField startDateField = new JTextField(10);
            JTextField endDateField = new JTextField(10);
            JPanel myPanel = new JPanel();
            myPanel.setLayout(new BoxLayout(myPanel, BoxLayout.Y_AXIS));
            myPanel.add(new JLabel("Please Enter Start and End Dates."));
            myPanel.add(Box.createVerticalStrut(20));
            myPanel.add(new JLabel("Start Date (MM/DD/YYYY):"));
            myPanel.add(startDateField);
            myPanel.add(Box.createVerticalStrut(10));
            myPanel.add(new JLabel("End Date (MM/DD/YYYY):"));
            myPanel.add(endDateField);
            int result = JOptionPane.showConfirmDialog(null, myPanel,
                    "Agenda Start/End Dates.", JOptionPane.CLOSED_OPTION);
            String startDate, endDate;
            if (result == JOptionPane.OK_OPTION) {
                startDate = startDateField.getText();
                endDate = endDateField.getText();
                if (startDate.length() == 10 && endDate.length() == 10) {
                    model.setViewType(startDate, endDate);
                    dayButton.setBackground(null);
                    weekButton.setBackground(null);
                    monthButton.setBackground(null);
                    agendaButton.setBackground(Color.YELLOW);
                }
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
     */
    public JPanel getPanel() {
        return panel;
    }
}
