package com.company;
/**
 * This file contains a class that represents the current calendar feature in the calendar program.
 * @author Viola Yasuda
 * @version 1.1 7/24/2021
 */
import javax.swing.*;
import java.awt.*;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.time.DayOfWeek;
import java.time.LocalDate;

/**
 * This class represents the current calendar in our calendar program.
 */
public class CurrentCalendarComponent {
    private LocalDate selectedDate;
    private final JPanel panel;
    private final CalendarModel model;
    public static final String[] DAYS_OF_WEEK = {"Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat"};
    public static final int CALENDAR_ROWS = 6;
    public static final int CALENDAR_COLUMNS = 7;

    /**
     * Constructs a CurrentCalendarComponent object.
     * @param modelParam the model to interact with
     */
    public CurrentCalendarComponent(CalendarModel modelParam) {
        //initializing instance variables
        model = modelParam;
        selectedDate = model.getDateToView();
        panel = new JPanel();

        //initialize headerPanel
        JPanel headerPanel = new JPanel();
        headerPanel.setLayout(new BoxLayout(headerPanel, BoxLayout.X_AXIS));
        JLabel monthYearLabel = new JLabel(selectedDate.getMonth() + " " + selectedDate.getYear() + " ");
        JButton backButton = new JButton("<");
        JButton nextButton = new JButton(">");
        headerPanel.add(monthYearLabel);
        headerPanel.add(backButton);
        headerPanel.add(nextButton);

        //initialize calendarPanel
        JPanel calendarPanel = new JPanel();
        calendarPanel.setLayout(new GridLayout(CALENDAR_ROWS + 1, CALENDAR_COLUMNS)); //+1 for the day labels
        updateCalendarPanel(calendarPanel);
        backButton.addActionListener(e -> {
            selectedDate = selectedDate.minusMonths(1);
            monthYearLabel.setText(selectedDate.getMonth() + " " + selectedDate.getYear() + " ");
            updateCalendarPanel(calendarPanel);
        });
        nextButton.addActionListener(e -> {
            selectedDate = selectedDate.plusMonths(1);
            monthYearLabel.setText(selectedDate.getMonth() + " " + selectedDate.getYear() + " ");
            updateCalendarPanel(calendarPanel);
        });

        //adding header and calendar panels to main panel
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
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

    /**
     * Represents a button corresponding to a day in a month.
     */
    public class CalendarNumberButton extends JButton {
        private final LocalDate date;
        private final CalendarModel calendarModel;

        /**
         * Constructs a CalendarNumberButton object.
         * @param dateParam the date corresponding to this CalendarNumberButton
         * @param modelParam the model to update whenever the button is pressed
         */
        public CalendarNumberButton(LocalDate dateParam, CalendarModel modelParam) {
            date = dateParam;
            calendarModel = modelParam;
            setText(Integer.toString(date.getDayOfMonth()));
            setFocusPainted(false);
            if (date.getMonthValue() == selectedDate.getMonthValue()) {
                addActionListener(e -> {
                    calendarModel.setDateToView(date);
                    selectedDate = date;
                });
            }
            else {
                setEnabled(false);
            }
            addFocusListener(new FocusListener() {
                /**
                 * Highlights button yellow if focus is gained.
                 * @param e the event in which focus on button is gained
                 */
                @Override
                public void focusGained(FocusEvent e) {
                    setBackground(Color.YELLOW);
                }

                /**
                 * Sets button's background to default color if focus is lost.
                 * @param e the event in which focus on button is lost
                 */
                @Override
                public void focusLost(FocusEvent e) {
                    setBackground(null);
                }
            });
        }
    }

    /**
     * Updates a panel to show the correct month display.
     * @param calendarPanel the panel to update
     */
    private void updateCalendarPanel(JPanel calendarPanel) {
        calendarPanel.removeAll();
        for (String s : DAYS_OF_WEEK) {
            calendarPanel.add(new JLabel(s, JLabel.CENTER));
        }
        LocalDate temp = LocalDate.of(selectedDate.getYear(), selectedDate.getMonthValue(), 1);
        while (!temp.getDayOfWeek().equals(DayOfWeek.SUNDAY)) {
            temp = temp.minusDays(1);
        }
        for (int i = 0; i < CALENDAR_ROWS * CALENDAR_COLUMNS; i++) {
            CalendarNumberButton button = new CalendarNumberButton(temp, model);
            calendarPanel.add(button);
            temp = temp.plusDays(1);
        }
    }
}
