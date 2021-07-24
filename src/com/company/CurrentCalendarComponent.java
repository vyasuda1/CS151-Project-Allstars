package com.company;
/**
 * This file contains a class that represents the current calendar feature in the calendar program.
 * @author Viola Yasuda
 * @version 1.1 7/24/2021
 */
import javax.swing.*;
import java.awt.*;
import java.time.DayOfWeek;
import java.time.LocalDate;

/**
 * This class represents the current calendar in our calendar program.
 */
public class CurrentCalendarComponent {
    private LocalDate selectedDate;
    private JPanel panel;
    private CalendarModel model;

    /**
     * Constructs a CurrentCalendarComponent object.
     * @param modelParam the model to interact with
     */
    public CurrentCalendarComponent(CalendarModel modelParam) {
        model = modelParam;
        panel = new JPanel();
        panel.setLayout(new GridLayout(7, 7));
        selectedDate = model.getDateToView();
        JButton[][] month = new JButton[6][7];
        LocalDate temp = LocalDate.of(selectedDate.getYear(), selectedDate.getMonthValue(), 1);
        while (!temp.getDayOfWeek().equals(DayOfWeek.SUNDAY)) {
            temp = temp.minusDays(1);
        }
        panel.add(new JButton("Sun"));
        panel.add(new JButton("Mon"));
        panel.add(new JButton("Tue"));
        panel.add(new JButton("Wed"));
        panel.add(new JButton("Thu"));
        panel.add(new JButton("Fri"));
        panel.add(new JButton("Sat"));
        for (int i = 0; i < month.length; i++) {
            for (int j = 0; j < month[i].length; j++) {
                month[i][j] = new JButton("" + temp.getDayOfMonth());
                month[i][j].setSize(5, 5);
                panel.add(month[i][j]);
                temp = temp.plusDays(1);
            }
        }
    }

    /**
     * Gets the panel with the buttons. Used in CalendarView to add to the frame.
     * @return panel
     */
    public JPanel getPanel() {
        return panel;
    }

}
