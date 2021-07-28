package com.company;

/**
 * File javadoc (description here)
 * @author
 * @version
 */

/**
 * Class javadoc (description here)
 */
public class NameFirstFormatter implements EventFormatter{
    /**
     * description here
     * @param event
     * @return
     */
    @Override
    public String formatEvent(Event event) {
        return event.getName() + ": " + event.getTimeInterval().toString() + "\n";
    }
}
