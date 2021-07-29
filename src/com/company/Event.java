package com.company;
/**
 * Contains a class that represents an event.
 * @author Viola Yasuda
 * @version 1.2 7/28/2021
 */
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.TreeSet;

/**
 * Event consists of a name, TimeInterval, a TreeSet of dates during which the event occurs, and a string with
 * the days of the week the event repeats on (if any).
 */
public class Event implements Comparable {
    private final String name;
    private final TimeInterval timeInterval;
    private final TreeSet<LocalDate> dates;
    private final String repeatedDays;

    public static final DateTimeFormatter FILE_FORMATTER = DateTimeFormatter.ofPattern("M/d/yy"); //for dates from file
    public static final DateTimeFormatter USER_FORMATTER = DateTimeFormatter.ofPattern("MM/dd/yyyy"); //for dates from user
    public static final String DAY_ABBREVIATIONS = "_MTWRFAS";

    /**
     * Constructs a one-time Event with a specified name, date, starting time, and ending time.
     * @param n an event's name
     * @param date the date an event occurs on
     * @param startTime the time an event starts at
     * @param endTime the time an event ends at
     * precondition: date must be in the format MM/DD/YY, startTime and endTime must be in the format HH:MM
     */
    public Event(String n, String date, String startTime, String endTime) {
        name = n;
        timeInterval = new TimeInterval(startTime, endTime);
        LocalDate localDate;
        if (date.length() == 10)
            localDate = LocalDate.parse(date, USER_FORMATTER);
        else
            localDate = LocalDate.parse(date, FILE_FORMATTER);
        dates = new TreeSet<>();
        dates.add(localDate);
        repeatedDays = null;
    }

    /**
     * Constructs a recurring Event with a specified name, repeated days of the week, starting time, ending time,
     * starting date, and ending date.
     * @param n an event's name
     * @param repeatDays the days of the week an event repeats on
     * @param startTime the time an event starts at
     * @param endTime the time an event ends at
     * @param startDate the date the event starts recurring
     * @param endDate the date the event stops recurring
     * precondition: startTime and endTime must be in the format HH:MM, startDate and endDate must be in the format
     * MM/DD/YY
     */
    public Event(String n, String repeatDays, String startTime, String endTime, String startDate, String endDate) {
        name = n;
        repeatedDays = repeatDays;
        timeInterval = new TimeInterval(startTime, endTime);
        LocalDate startingDate = LocalDate.parse(startDate, FILE_FORMATTER);
        LocalDate endingDate = LocalDate.parse(endDate, FILE_FORMATTER);
        dates = new TreeSet<>();
        addDates(repeatedDays, startingDate, endingDate);
    }

    /**
     * Determines if event conflicts with another event.
     * @param other the other event
     * @return true if events conflict, false otherwise
     */
    public boolean conflicts(Event other) {
        return datesOverlap(other) && timeInterval.overlaps(other.getTimeInterval());
    }

    /**
     * Gets the name of the event.
     * @return the name of the event
     */
    public String getName() {
        return name;
    }

    /**
     * Gets the dates of the event.
     * @return dates
     */
    public TreeSet<LocalDate> getDates() {
        return dates;
    }

    /**
     * Gets the time interval of the event.
     * @return the time interval
     */
    public TimeInterval getTimeInterval() {
        return timeInterval;
    }

    /**
     * Compares this event to another event
     * @param o the other event being compared to this one
     * @return a negative value if this event occurs before o, a positive value if this event occurs after o, and 0 if
     * they occur at the same time
     */
    @Override
    public int compareTo(Object o) {
        Event other = (Event) o;
        if (dates.first().isBefore(other.getDates().first())) {
            return -1;
        }
        else if (dates.first().isAfter(other.getDates().first())) {
            return 1;
        }
        else {
            return timeInterval.compareTo(other.getTimeInterval());
        }
    }

    /**
     * Determines if dates of an event overlap with dates of another event.
     * @param other the other event
     * @return true if they overlap, false otherwise
     */
    private boolean datesOverlap(Event other) {
        for (LocalDate thisDate : dates)
            for (LocalDate otherDate : other.getDates())
                if (thisDate.isEqual(otherDate))
                    return true;
        return false;
    }

    /**
     * Calculates all date the event recurs on and adds them to dates.
     * @param dayAbbrevs a sequence of day abbreviations
     * @param startDate the date the event starts recurring
     * @param endDate the date the event stops recurring
     * precondition: dayAbbrevs must consist only of characters that DAY_ABBREVIATIONS also contains
     */
    private void addDates(String dayAbbrevs, LocalDate startDate, LocalDate endDate) {
        ArrayList<Integer> daySequence = new ArrayList<>();
        for (int i = 0; i < dayAbbrevs.length(); i++) {
            String day = dayAbbrevs.substring(i, i + 1);
            daySequence.add(DAY_ABBREVIATIONS.indexOf(day));
        }
        LocalDate date = startDate;
        while (!date.isAfter(endDate)) {
            for (int d : daySequence) {
                if (date.getDayOfWeek().getValue() == d) {
                    dates.add(date);
                    break;
                }
            }
            date = date.plusDays(1);
        }
    }
}
