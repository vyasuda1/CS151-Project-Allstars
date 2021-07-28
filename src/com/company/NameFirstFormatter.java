package com.company;
/**
 * This file contains a class that implements EventFormatter that formats the event in a name first format.
 * @author Haider Almandeel
 * @version 1.0 7/27/2021
 */

/**
 * Represents an event formatter that formats events as "Name of Event: startTime - endTime"
 */
public class NameFirstFormatter implements EventFormatter{
    /**
     * Returns a string swapping the time and event with the name being shown first.
     * @author Haider Almandeel
     * @version 1.0 7/27/2021
     */
    @Override
    public String formatEvent(Event event) {
        return event.getName() + ": " + event.getTimeInterval().toString() + "\n";
    }
}
