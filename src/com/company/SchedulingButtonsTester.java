package com.company;
/**
 *
 * @author Nolen Johnson
 * @version 1.0 7/20/21
 */

import javax.swing.*;
import java.awt.*;

/**
 *
 */
public class SchedulingButtonsTester {
    /**
     *
     * @param args
     */
    public static void main(String[] args) {
        JFrame frame = new JFrame();
        CalendarModel m = new CalendarModel();
        SchedulingButtons buttons = new SchedulingButtons(m);
        frame.add(buttons.getPanel());
        frame.setSize(300,300);
        frame.setLayout(new FlowLayout());
        frame.setVisible(true);
    }
}
