package com.company;

/**
 * This file contains a class EventFormatter that formats the event starting
 * with either the name first or the time first.
 * @author Haider Almandeel
 * @version 1.0 7/27/2021
 */

public interface EventFormatter {
    /**
     * Calls the unambiguous formatEvent method and returns a string.
     * @author Haider Almandeel
     * @version 1.0 7/27/2021
     */
    public String formatEvent(Event event);
}
