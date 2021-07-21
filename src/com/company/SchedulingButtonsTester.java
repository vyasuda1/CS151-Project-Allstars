import javax.swing.*;
import java.awt.*;

public class SchedulingButtonsTester {
    public static void main(String[] args) {


        JFrame frame = new JFrame();
        SchedulingButtons buttons = new SchedulingButtons();
        frame.add(buttons.getPanel());
        frame.setSize(300,300);
        frame.setLayout(new FlowLayout());
        frame.setVisible(true);
    }
}
