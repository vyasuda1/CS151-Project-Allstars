package com.company;

/**
 * This file contains a class that implements EventFormatter that formats the event in a time first format.
 * @author Haider Almandeel, Viola Yasuda
 * @version 1.0 7/27/2021
 */

public class TimeFirstFormatter implements EventFormatter{
    /**
     * Returns a string swapping the time and event with the time being shown first.
     * @author Haider Almandeel, Viola Yasuda
     * @version 1.0 7/27/2021
     */
    @Override
    public String formatEvent(Event event) {
        return event.getTimeInterval().toString() + ": " + event.getName() + "\n";
    }
}
