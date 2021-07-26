package com.company;
/**
 * This file holds a class that tests the current calendar part of the calendar program.
 * @author Viola Yasuda
 * @verison 1.1 7/25/2021
 */

import javax.swing.*;

/**
 * Makes a frame and CurrentCalendarComponent object. Tests the current calendar component.
 */
public class CurrentCalendarComponentTester {
    /**
     * To compile, comment out call to notifyCalendarView() in setEventsToView() in CalendarModel.java and uncomment
     * the print statement in CalendarModel.java.
     * @param args any arguments passed during run time
     */
    public static void main(String[] args) {
        CalendarModel model = new CalendarModel();
        JFrame frame = new JFrame();
        CurrentCalendarComponent currentCalendarComponent = new CurrentCalendarComponent(model);
        JPanel currentCalendar = currentCalendarComponent.getPanel();
        frame.add(currentCalendar);
        frame.setTitle("CurrentCalendarComponentTester.java");
        frame.setSize(290, 200);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}
