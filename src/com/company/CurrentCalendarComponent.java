package com.company;
/**
 * This file contains a class that represents the current calendar feature in the calendar program.
 * @author Viola Yasuda
 * @version 1.1 7/24/2021
 */
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.DayOfWeek;
import java.time.LocalDate;

/**
 * This class represents the current calendar in our calendar program.
 */
public class CurrentCalendarComponent {
    private JPanel panel;
    private CalendarModel model;
    public static final String[] DAYS_OF_WEEK = {"Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat"};

    /**
     * Constructs a CurrentCalendarComponent object.
     * @param modelParam the model to interact with
     */
    public CurrentCalendarComponent(CalendarModel modelParam) {
        model = modelParam;
        panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        JPanel headerPanel = new JPanel();
        JPanel calendarPanel = new JPanel();

        //initialize headerPanel
        headerPanel.setLayout(new BoxLayout(headerPanel, BoxLayout.X_AXIS));
        JLabel monthYearLabel = new JLabel(model.getDateToView().getMonth() + " " + model.getDateToView().getYear() + " ");
        JButton backButton = new JButton("<");
        JButton nextButton = new JButton(">");
        headerPanel.add(monthYearLabel);
        headerPanel.add(backButton);
        headerPanel.add(nextButton);

        //initialize calendarPanel
        calendarPanel.setLayout(new GridLayout(7, 7));
        for (int i = 0; i < DAYS_OF_WEEK.length; i++) {
            calendarPanel.add(new JLabel(DAYS_OF_WEEK[i], JLabel.CENTER));
        }
        LocalDate temp = LocalDate.of(model.getDateToView().getYear(), model.getDateToView().getMonthValue(), 1);
        while (!temp.getDayOfWeek().equals(DayOfWeek.SUNDAY)) {
            temp = temp.minusDays(1);
        }
        for (int i = 0; i < 42; i++) {
            CalendarNumberButton button = new CalendarNumberButton(temp, model);
            calendarPanel.add(button);
            temp = temp.plusDays(1);
        }

        panel.add(headerPanel);
        panel.add(calendarPanel);
    }

    /**
     * Gets the panel with the buttons. Used in CalendarView to add to the frame.
     * @return panel
     */
    public JPanel getPanel() {
        return panel;
    }

    public class CalendarNumberButton extends JButton {
        private LocalDate date;
        private CalendarModel calendarModel;
        public CalendarNumberButton(LocalDate dateParam, CalendarModel modelParam) {
            date = dateParam;
            calendarModel = modelParam;
            setText(Integer.toString(date.getDayOfMonth()));
            if (date.getMonthValue() == calendarModel.getDateToView().getMonthValue()) {
                addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        calendarModel.setDateToView(date);
                    }
                });
            }
            else {
                setEnabled(false);
            }
        }
        public LocalDate getDate() {
            return date;
        }
        public void setDate(LocalDate date) {
            this.date = date;
            setText(Integer.toString(date.getDayOfMonth()));
        }
    }

}
