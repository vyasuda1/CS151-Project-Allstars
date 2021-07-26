package com.company;
/**
 * -Description of file-
 * @author Haider Almandeel
 * @version 1.0 7/21/2021
 */

import javax.swing.*;

/**
 * -Description of class-
 */
public class ViewChangerButtonsTester {
    /**
     * To compile, comment out call to notifyCalendarView() in setEventsToView() in CalendarModel.java and uncomment
     * the print statement in CalendarModel.java.
     * @param args -Description of args-
     */
    public static void main(String[] args) {
        CalendarModel model = new CalendarModel();
        JFrame frame = new JFrame();
        ViewChangerButtons viewChangerButtons = new ViewChangerButtons(model);
        JPanel viewButtons = viewChangerButtons.getPanel();
        frame.add(viewButtons);
        frame.setTitle("ViewChangerButtonsTester.java");
        frame.setSize(300, 100);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}
