package com.company;
/**
 * Contains a class with a main method that tests the CalendarModel and CalendarView classes.
 * @author Viola Yasuda
 * @version 1.0 7/19/2021
 */

/**
 * CalendarTester contains a main method that tests the CalendarModel and CalendarView classes.
 */
public class CalendarTester {
    /**
     * Produces a main menu that allows the user to interact with a calendar.
     * @param args any arguments passed during run time
     */
    public static void main(String [] args) {
        CalendarModel model = new CalendarModel();
        CalendarView view = new CalendarView(model);
        view.display();
    }
}
