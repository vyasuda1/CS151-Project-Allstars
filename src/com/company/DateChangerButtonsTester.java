package com.company;
/**
 * This file contains a tester class that tests the DateChangerButtons class.
 * @author Viola Yasuda
 * @version 1.0 7/19/21
 */

import javax.swing.*;

/**
 * Makes a frame and DateChangerButtons object. Tests the date changing buttons.
 */
public class DateChangerButtonsTester {
    /**
     * To compile, comment out call to notifyCalendarView() in setEventsToView() in CalendarModel.java and uncomment
     * the print statement in CalendarModel.java.
     * @param args any arguments passed during run time
     */
    public static void main(String[] args) {
        CalendarModel model = new CalendarModel();
        model.setViewType("Week");
        //model.setViewType("Month");
        JFrame frame = new JFrame();
        DateChangerButtons dateChangerButtons = new DateChangerButtons(model);
        JPanel dateButtons = dateChangerButtons.getPanel();
        frame.add(dateButtons);
        frame.setTitle("DateChangerButtonsTester.java");
        frame.setSize(290, 200);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}
