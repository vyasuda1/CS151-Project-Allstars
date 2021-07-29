package com.company;

/**
 * This file contains a class that formats the event in a time-first format.
 * @author Haider Almandeel, Nolen Johnson, Viola Yasuda
 * @version 1.0 7/27/2021
 */

/**
 * Represents an event formatter that formats events as "startTime - endTime: Name of Event".
 */
public class TimeFirstFormatter implements EventFormatter{
    /**
     * Returns a string with an event's information formatted time-interval first, then name.
     * @param event the event whose information will be formatted
     * @return a string formatted with an event's information
     */
    @Override
    public String formatEvent(Event event) {
        return event.getTimeInterval().toString() + ": " + event.getName() + "\n";
    }
}
