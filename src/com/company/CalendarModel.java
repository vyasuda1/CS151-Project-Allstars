package com.company;
/**
 * Contains a class thar represents a calendar.
 * @author Viola Yasuda
 * @version 1.0 6/14/21
 */
import java.io.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

/**
 * CalendarModel represents a calendar and defines an underlying data structure to hold events.
 */
public class CalendarModel {
    private ArrayList<Event> events; // can be one-time or recurring
    private String viewType; // can be either "Day", "Week", "Month", or "Agenda"
    private LocalDate dateToView; // default will be whatever date today is
    private CalendarView calendarView;
    private String eventsToView; // will be used to set the text area in calendarView

    public static final DateTimeFormatter FILE_FORMATTER = DateTimeFormatter.ofPattern("M/d/yy"); //for saveToFile
    public static final DateTimeFormatter USER_FORMATTER = DateTimeFormatter.ofPattern("MM/dd/yyyy"); //for dates from user

    /**
     * Constructs a MyCalendar object from the contents of events.txt.
     */
    public CalendarModel() {
        viewType = "Day";
        dateToView = LocalDate.now();

        // initializing events ArrayList
        events = new ArrayList<>();
        File file = new File("events.txt");
        try {
            //load events from events.txt
            Scanner in = new Scanner(file);
            String name, temp, date, repeatDays, startTime, endTime, startDate, endDate;
            while (in.hasNextLine()) {
                name = in.nextLine();
                temp = in.next();
                if (temp.contains("/")) {
                    date = temp;
                    startTime = in.next();
                    endTime = in.next();
                    addEvent(new Event(name, date, startTime, endTime));
                }
                else {
                    repeatDays = temp;
                    startTime = in.next();
                    endTime = in.next();
                    startDate = in.next();
                    endDate = in.next();
                    addEvent(new Event(name, repeatDays, startTime, endTime, startDate, endDate));
                }
                in.nextLine();
            }
            Collections.sort(events);
            System.out.println("Loading is done!");
        }
        catch (FileNotFoundException e) {
            e.printStackTrace();
            System.out.println("Loading failed.");
        }

        //initializing contents of text area
        eventsToView = displayDayView(dateToView);
    }

    /**
     * Checks if an event conflicts with any existing events in the calendar and adds it to the calendar if not.
     * @param newEvent the event to be added
     */
    public void addEvent(Event newEvent) {
        for (Event e : events) {
            if (e.conflicts(newEvent)) {
                System.out.println("Error: cannot add " + newEvent.getName() +
                        " due to a time conflict with " + e.getName() + ".");
                return;
            }
        }
        events.add(newEvent);
        Collections.sort(events);
    }


    /**
     * Saves current events in output.txt.
     */
    public void saveToFile() {
        try {
            File myFile = new File("output.txt");
            FileWriter fWriter = new FileWriter(myFile);
            PrintWriter outputFile = new PrintWriter(fWriter);
            for (Event e : events) {
                outputFile.println(e.getName());
                if (e.isRecurring()) {
                    outputFile.println(e.getRepeatedDays() + " " + e.getTimeInterval().getStartTime() + " " +
                            e.getTimeInterval().getEndTime() + " " + e.getDates().first().format(FILE_FORMATTER) + " " +
                            e.getDates().last().format(FILE_FORMATTER));
                }
                else {
                    outputFile.println(e.getDates().first().format(FILE_FORMATTER) + " " +
                            e.getTimeInterval().getStartTime() + " " + e.getTimeInterval().getEndTime());
                }
            }
            outputFile.close();
        }
        catch (IOException exception) {
            exception.printStackTrace();
        }
    }

    /**
     * Registers a CalendarView.
     * @param view the CalendarView to register
     */
    public void registerCalendarView(CalendarView view) {
        calendarView = view;
    }

    /**
     * Notifies the CalendarView of any changes made to CalendarModel.
     */
    public void notifyCalendarView() {
        calendarView.update(eventsToView);
    }

    public String getEventsToView() {
        return eventsToView;
    }

    /**
     * Returns a string displaying a date along with scheduled events in order of event start time.
     * @param date the date to be displayed
     * @return a string with a date and events
     */
    private String displayDayView(LocalDate date) {
        ArrayList<Event> dayEvents = new ArrayList<>();
        String dateAndEvents = date.getDayOfWeek() + ", " + date.getMonth() + " " + date.getDayOfMonth() + ", " +
                date.getYear() + "\n";
        for (Event e : events) {
            if (e.getDates().contains(date)) {
                dayEvents.add(new Event(e.getName(), date.format(USER_FORMATTER),
                        e.getTimeInterval().getStartTime().toString(), e.getTimeInterval().getEndTime().toString()));
            }
        }
        Collections.sort(dayEvents);
        for (Event e : dayEvents) {
            dateAndEvents += e.getName() + ": " + e.getTimeInterval().toString() + "\n";
        }
        return dateAndEvents;
    }
}
