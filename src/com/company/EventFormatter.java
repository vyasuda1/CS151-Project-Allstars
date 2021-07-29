package com.company;
/**
 * This file contains a class EventFormatter that formats the event starting
 * with either the name first or the time first.
 * @author Haider Almandeel, Nolen Johnson, Viola Yasuda
 * @version 1.0 7/27/2021
 */

/**
 * An interface that formats the information of an event in different ways in strings.
 */
public interface EventFormatter {
    /**
     * Returns a string with an event's information formatted in a certain way.
     * @param event the event whose information will be formatted
     * @return a string with the event's information
     */
    String formatEvent(Event event);
}
