package com.company;
/**
 * This file holds a class that tests the current calendar part of the calendar program.
 * @author Viola Yasuda
 * @verison 1.0 7/24/2021
 */

import javax.swing.*;

/**
 * Makes a frame and text area. Tests the current calendar component.
 */
public class CurrentCalendarComponentTester {
    /**
     * Tests CurrentCalendarComponent class.
     * @param args any arguments passed during run time
     */
    public static void main(String[] args) {
        CalendarModel model = new CalendarModel();
        JFrame frame = new JFrame();
        CurrentCalendarComponent currentCalendarComponent = new CurrentCalendarComponent(model);
        JPanel currentCalendar = currentCalendarComponent.getPanel();
        frame.add(currentCalendar);
        frame.setTitle("DateChangerButtonsTester.java");
        frame.setSize(290, 200);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}
