package com.company;
/**
 * Contains a class thar represents a calendar. Added methods related to the mvc architecture and changing the date
 * to view. Added a method to set viewType to Agenda and code in setEventsToView for Agenda use case.
 * @author Haider Almandeel, Nolen Johnson, Viola Yasuda
 * @version 1.2 7/20/21
 */
import java.io.*;
import java.time.DayOfWeek;
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
    private CalendarView calendarView; // for mvc architecture
    private String eventsToView; // will be used to set the text area in calendarView
    private LocalDate agendaStartDate; // will be used for agenda view
    private LocalDate agendaEndDate; // will be used for agenda view

    public static final DateTimeFormatter FILE_FORMATTER = DateTimeFormatter.ofPattern("M/d/yy"); //for saveToFile
    public static final DateTimeFormatter USER_FORMATTER = DateTimeFormatter.ofPattern("MM/dd/yyyy"); //for dates from user

    /**
     * Constructs a MyCalendar object from the contents of events.txt.
     * @author Viola Yasuda
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
     * For From File button (may need to reformat to parse Math Class;2014;1;2;MWF;17;18;)
     * @param filename the name of the file to load events from
     * @author Nolen Johnson, Viola Yasuda
     */
    public void loadFile(String filename) {
        File file = new File(filename);
        try {
            //load events from filename
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
    }

    /**
     * Gets the ArrayList of events. Will be used in SchedulingButtons class.
     * @return events ArrayList
     * @author Viola Yasuda
     */
    public ArrayList<Event> getEvents() {
        return events;
    }

    /**
     * Checks if an event conflicts with any existing events in the calendar and adds it to the calendar if not.
     * @param newEvent the event to be added
     * @author Viola Yasuda
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
     * @author Viola Yasuda
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
     * @author Viola Yasuda
     */
    public void registerCalendarView(CalendarView view) {
        calendarView = view;
    }

    /**
     * Notifies the CalendarView of any changes made to CalendarModel.
     * @author Viola Yasuda
     */
    public void notifyCalendarView() {
        calendarView.update(eventsToView);
    }

    /**
     * Updates the string (eventsToView) that will be used to set the text area in CalendarView.
     * @author Viola Yasuda
     */
    public void setEventsToView() {
        if (viewType.equals("Day")) {
            eventsToView = displayDayView(dateToView);
        }
        else if (viewType.equals("Week")) {
            eventsToView = "";
            LocalDate temp = dateToView;
            while (!temp.getDayOfWeek().equals(DayOfWeek.SUNDAY)) {
                temp = temp.minusDays(1);
            }
            for (int i = 0; i < 7; i++) {
                eventsToView += displayDayView(temp);
                temp = temp.plusDays(1);
            }
        }
        else if (viewType.equals("Month")) {
            eventsToView = "";
            LocalDate temp = LocalDate.of(dateToView.getYear(), dateToView.getMonthValue(), 1);
            while (!temp.isEqual(LocalDate.of(dateToView.getYear(), dateToView.getMonthValue() + 1, 1))) {
                eventsToView += displayDayView(temp);
                temp = temp.plusDays(1);
            }
        }
        else if (viewType.equals("Agenda")) {
            eventsToView = "";
            LocalDate temp = agendaStartDate;
            while (!temp.isAfter(agendaEndDate)) {
                eventsToView += displayDayView(temp);
                temp = temp.plusDays(1);
            }
        }
        else {
            System.out.println("Invalid view type for showing events.");
        }
        notifyCalendarView();
    }

    /**
     * Gets the string (eventsToView) that will be used to set the text area in CalendarView. We might only use this for
     * testing/debugging (not in final product).
     * @return a string with a list of all events to view
     * @author Viola Yasuda
     */
    public String getEventsToView() {
        return eventsToView;
    }

    /**
     * Sets the view type. Should only take "Day", "Week", and "Month" for the parameter.
     * @param viewType the time span type to view
     * @author Viola Yasuda
     */
    public void setViewType(String viewType) {
        this.viewType = viewType;
        setEventsToView();
    }

    /**
     * Sets the view type to Agenda view.
     * @param startDate
     * @param endDate
     * @author Haider Almandeel
     */
    public void setViewType(String startDate, String endDate) {
        viewType = "Agenda";
        //agendaStartDate = (format startDate from String to LocalDate)
        //agendaEndDate = (format endDate from String to LocalDate)
        setEventsToView();
    }

    /**
     * Used when clicking Today button or dates in current calendar.
     * @param d the date to view
     * @author Viola Yasuda
     */
    public void setDateToView(LocalDate d) {
        dateToView = d;
        setEventsToView();
    }

    /**
     * Sets dateToView to a date either one day, week, or month after the current dateToView depending on the current
     * viewType
     * @author Viola Yasuda
     */
    public void setNextDateToView() {
        if (viewType.equals("Day")) {
            dateToView = dateToView.plusDays(1);
        }
        else if (viewType.equals("Week")) {
            dateToView = dateToView.plusWeeks(1);
        }
        else if (viewType.equals("Month")) {
            dateToView = dateToView.plusMonths(1);
        }
        else {
            System.out.println("Invalid view type for moving to next date.");
        }
        setEventsToView();
    }

    /**
     * Sets dateToView to a date either one day, week, or month before the current dateToView depending on the current
     * viewType.
     * @author Viola Yasuda
     */
    public void setPreviousDateToView() {
        if (viewType.equals("Day")) {
            dateToView = dateToView.minusDays(1);
        }
        else if (viewType.equals("Week")) {
            dateToView = dateToView.minusWeeks(1);
        }
        else if (viewType.equals("Month")) {
            dateToView = dateToView.minusMonths(1);
        }
        else {
            System.out.println("Invalid view type for moving to next date.");
        }
        setEventsToView();
    }

    /**
     * Returns a string displaying a date along with scheduled events in order of event start time.
     * @param date the date to be displayed
     * @return a string with a date and events
     * @author Viola Yasuda
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
        dateAndEvents += "\n";
        return dateAndEvents;
    }
}
