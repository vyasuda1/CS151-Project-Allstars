package com.company;
/**
 * File description here
 * @author Nolen Johnson
 * @version 1.0 7/20/2021
 */

import javax.swing.*;
import java.awt.*;

/**
 * Class description here
 */
public class SchedulingButtonsTester {
    /**
     * Method description here
     * @param args param description here
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
