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
    public static final String[] DAYS_OF_WEEK = {"Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat"};

    /**
     * Constructs a CurrentCalendarComponent object.
     * @param modelParam the model to interact with
     */
    public CurrentCalendarComponent(CalendarModel modelParam) {
        model = modelParam;
        updateCurrentCalendar(model.getDateToView());
    }

    /**
     * Gets the panel with the buttons. Used in CalendarView to add to the frame.
     * @return panel
     */
    public JPanel getPanel() {
        return panel;
    }

    public void updateCurrentCalendar(LocalDate date) {
        selectedDate = date;
        panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        JPanel headerPanel = new JPanel();
        headerPanel.setLayout(new BoxLayout(headerPanel, BoxLayout.X_AXIS));
        headerPanel.add(new JLabel(selectedDate.getMonth() + " " + selectedDate.getYear() + " ", JLabel.CENTER));
        headerPanel.add(new JButton("<"));
        headerPanel.add(new JButton(">"));
        JPanel calendarPanel = new JPanel();
        calendarPanel.setLayout(new GridLayout(7, 7));
        JButton[][] month = new JButton[6][7];
        LocalDate temp = LocalDate.of(selectedDate.getYear(), selectedDate.getMonthValue(), 1);
        while (!temp.getDayOfWeek().equals(DayOfWeek.SUNDAY)) {
            temp = temp.minusDays(1);
        }
        for (int i = 0; i < DAYS_OF_WEEK.length; i++) {
            JLabel dayLabel = new JLabel(DAYS_OF_WEEK[i], JLabel.CENTER);
            calendarPanel.add(dayLabel);
        }
        for (int i = 0; i < month.length; i++) {
            for (int j = 0; j < month[i].length; j++) {
                month[i][j] = new JButton("" + temp.getDayOfMonth());
                if (temp.getMonthValue() != selectedDate.getMonthValue()) {
                    month[i][j].setEnabled(false);
                }
                else {
                    if (temp.isEqual(selectedDate)) {
                        month[i][j].setBackground(Color.YELLOW);
                    }
                }
                calendarPanel.add(month[i][j]);
                temp = temp.plusDays(1);
            }
        }
        panel.add(headerPanel);
        panel.add(calendarPanel);
    }

}
