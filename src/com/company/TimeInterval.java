package com.company;
/**
 * Contains a class that represents a time interval.
 * @author Viola Yasuda
 * @version 1.0 6/14/21
 */
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

/**
 * TimeInterval represents an interval of time suitable for events and has a start time and an end time.
 */
public class TimeInterval implements Comparable {
    private LocalTime startTime;
    private LocalTime endTime;

    public static final DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern("H:mm");

    /**
     * Constructs a TimeInterval with a specified start time and end time.
     * @param startT a starting time
     * @param endT an ending time
     * precondition: startT and endT must be in the format "HH:MM", startT must be a time before endT
     */
    public TimeInterval(String startT, String endT) {
        startTime = LocalTime.parse(startT, TIME_FORMATTER);
        endTime = LocalTime.parse(endT, TIME_FORMATTER);
    }

    /**
     * Determines if this time interval overlaps with another interval.
     * @param other the other interval being compared to this one
     * @return true if the intervals overlap and false if not.
     */
    public boolean overlaps(TimeInterval other) {
        //overlap case 1: time intervals start at same time
        boolean startTimesAreSame = startTime.equals(other.getStartTime());
        //overlap case 2: other starts after this starts and before this ends
        boolean otherStartsDuringThis = other.getStartTime().isAfter(startTime) &&
                other.getStartTime().isBefore(endTime);
        //overlap case 3: this starts after other starts and before other ends
        boolean thisStartsDuringOther = startTime.isAfter(other.getStartTime()) &&
                startTime.isBefore(other.getEndTime());
        return startTimesAreSame || otherStartsDuringThis || thisStartsDuringOther;
    }

    /**
     * Gets the time interval's starting time.
     * @return the starting time of the time interval
     */
    public LocalTime getStartTime() {
        return startTime;
    }

    /**
     * Gets the time interval's ending time.
     * @return the ending time of the time interval
     */
    public LocalTime getEndTime() {
        return endTime;
    }

    /**
     * Puts information about time interval into a string.
     * @return a string with the starting time and ending time of the time interval
     */
    @Override
    public String toString() {
        return startTime + "-" + endTime;
    }

    /**
     * Compares this time interval to another.
     * @param o the time interval being compared to
     * @return a negative number if this time interval is earlier, a positive number if this time interval is later,
     * or 0 if this time inverval occurs at the same time as the other
     */
    @Override
    public int compareTo(Object o) {
        TimeInterval other = (TimeInterval) o;
        if (startTime.isBefore(other.getStartTime())) {
            return -1;
        }
        else if (startTime.isAfter(other.getStartTime())) {
            return 1;
        }
        else if (endTime.isBefore(other.getEndTime())) {
            return -1;
        }
        else if (endTime.isAfter(other.getEndTime())) {
            return 1;
        }
        else {
            return 0;
        }
    }
}
