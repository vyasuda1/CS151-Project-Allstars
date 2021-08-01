package com.company;
/**
 * Contains a class thar represents a calendar.
 * @author Haider Almandeel, Nolen Johnson, Viola Yasuda
 * @version 1.2 7/23/2021
 */
import javax.swing.*;
import java.io.*;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

/**
 * CalendarModel represents a calendar and defines an underlying data structure to hold events.
 */
public class CalendarModel {
    private final ArrayList<Event> events; // can be one-time or recurring
    private String viewType; // can be either "Day", "Week", "Month", or "Agenda"
    private LocalDate dateToView; // default will be whatever date today is
    private CalendarView calendarView; // for mvc architecture
    private String eventsToView; // will be used to set the text area in calendarView
    private LocalDate agendaStartDate; // will be used for agenda view
    private LocalDate agendaEndDate; // will be used for agenda view
    private EventFormatter formatter; //for the strategy patter requirement. Formats the events in the text area

    public static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("MM/dd/yyyy");

    /**
     * Constructs a MyCalendar object from the contents of events.txt.
     * @author Viola Yasuda
     */
    public CalendarModel() {
        viewType = "Day";
        dateToView = LocalDate.now();
        events = new ArrayList<>(); // initializing events ArrayList
        loadFile("events.txt"); //populating events ArrayList
        formatter = new NameFirstFormatter(); //initializing formatter
        eventsToView = dayViewAsString(dateToView); //initializing contents of text area
    }

    /**
     * Gets the ArrayList of events.
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
                JPanel panel = new JPanel();
                JOptionPane.showMessageDialog(panel, "Error: cannot add " + newEvent.getName() + " due to a " +
                        "time conflict with " + e.getName() + ".", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
        }
        events.add(newEvent);
        Collections.sort(events);
    }

    /**
     * Sets the view type.
     * @param viewType the time span type to view
     * precondition: Should only take "Day", "Week", and "Month" for the parameter.
     * @author Viola Yasuda
     */
    public void setViewType(String viewType) {
        this.viewType = viewType;
        setEventsToView();
    }

    /**
     * Sets the view type to Agenda view.
     * @param startDate the start date of the time span to view
     * @param endDate the end date of the time span to view
     * @author Haider Almandeel, Nolen Johnson, Viola Yasuda
     */
    public void setViewType(String startDate, String endDate) {
        String previousViewType = viewType;
        try {
        viewType = "Agenda";
        agendaStartDate = LocalDate.parse(startDate, DATE_FORMATTER);
        agendaEndDate = LocalDate.parse(endDate, DATE_FORMATTER);
        setEventsToView();
        }
        catch (DateTimeParseException dateTimeParseException) {
            viewType = previousViewType;
            JPanel panel = new JPanel();
            JOptionPane.showMessageDialog(panel, "Invalid inputs or formats for one or both dates.\n Required" +
                    " format: MM/DD/YYYY.\n Example: type 01/02/2021 for January 2nd, 2021", "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Gets the date to view.
     * @return the date to view
     * @author Viola Yasuda
     */
    public LocalDate getDateToView() {
        return dateToView;
    }

    /**
     * Sets date to view to a specified date. Used when clicking Today button or dates in current calendar.
     * @param dateParam the date to view
     * @author Viola Yasuda
     */
    public void setDateToView(LocalDate dateParam) {
        dateToView = dateParam;
        setEventsToView();
    }

    /**
     * Sets dateToView to a date either one day, week, or month after the current dateToView depending on the
     * current viewType.
     * @author Viola Yasuda
     */
    public void setNextDateToView() {
        switch (viewType) {
            case "Day" -> dateToView = dateToView.plusDays(1);
            case "Week" -> dateToView = dateToView.plusWeeks(1);
            case "Month" -> dateToView = dateToView.plusMonths(1);
            default ->  {
                JPanel panel = new JPanel();
                JOptionPane.showMessageDialog(panel, "Invalid view type for moving date forward.", "Error",
                        JOptionPane.ERROR_MESSAGE);
            }
        }
        setEventsToView();
    }

    /**
     * Sets dateToView to a date either one day, week, or month before the current dateToView depending on the
     * current viewType.
     * @author Viola Yasuda
     */
    public void setPreviousDateToView() {
        switch (viewType) {
            case "Day" -> dateToView = dateToView.minusDays(1);
            case "Week" -> dateToView = dateToView.minusWeeks(1);
            case "Month" -> dateToView = dateToView.minusMonths(1);
            default ->  {
                JPanel panel = new JPanel();
                JOptionPane.showMessageDialog(panel, "Invalid view type for moving date back.", "Error",
                        JOptionPane.ERROR_MESSAGE);
            }
        }
        setEventsToView();
    }

    /**
     * Gets the string (eventsToView) that will be used to set the text area in CalendarView.
     * @return a string with a list of all events to view
     * @author Viola Yasuda
     */
    public String getEventsToView() {
        return eventsToView;
    }

    /**
     * Updates the string (eventsToView) that will be used to set the text area in CalendarView.
     * precondition: agendaStartDate occurs before agendaEndDate
     * @author Viola Yasuda
     */
    public void setEventsToView() {
        switch (viewType) {
            case "Day" -> eventsToView = dayViewAsString(dateToView);
            case "Week" -> {
                eventsToView = "";
                LocalDate temp = dateToView;
                while (!temp.getDayOfWeek().equals(DayOfWeek.SUNDAY)) {
                    temp = temp.minusDays(1);
                }
                for (int i = 0; i < 7; i++) {
                    eventsToView += dayViewAsString(temp);
                    temp = temp.plusDays(1);
                }
            }
            case "Month" -> {
                eventsToView = "";
                LocalDate temp = LocalDate.of(dateToView.getYear(), dateToView.getMonthValue(), 1);
                while (temp.getMonth().equals(dateToView.getMonth())) {
                    eventsToView += dayViewAsString(temp);
                    temp = temp.plusDays(1);
                }
            }
            case "Agenda" -> {
                eventsToView = "";
                LocalDate temp = agendaStartDate;
                while (!temp.isAfter(agendaEndDate)) {
                    eventsToView += dayViewAsString(temp);
                    temp = temp.plusDays(1);
                }
            }
            default -> {
                JPanel panel = new JPanel();
                JOptionPane.showMessageDialog(panel, "Invalid view type for showing events.", "Error",
                        JOptionPane.ERROR_MESSAGE);
            }
        }
        notifyCalendarView();
    }

    /**
     * Loads events from a specified file.
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
        }
        catch (FileNotFoundException e) {
            JPanel panel = new JPanel();
            JOptionPane.showMessageDialog(panel, "No file by that name was found. Loading of events " +
                    "has failed.", "Error", JOptionPane.ERROR_MESSAGE);
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
     * Sets the formatter to a specific EventFormatter
     * @param formatter the formatter to set this formatter to
     * @author Viola Yasuda
     */
    public void setFormatter(EventFormatter formatter) {
        this.formatter = formatter;
        setEventsToView();
    }

    /**
     * Returns a string displaying a date along with scheduled events in order of event start time.
     * @param date the date to be displayed
     * @return a string with a date and events
     * @author Viola Yasuda
     */
    private String dayViewAsString(LocalDate date) {
        ArrayList<Event> dayEvents = new ArrayList<>();
        String dateAndEvents = date.getDayOfWeek() + ", " + date.getMonth() + " " + date.getDayOfMonth() + ", " +
                date.getYear() + "\n";
        for (Event e : events) {
            if (e.getDates().contains(date)) {
                dayEvents.add(new Event(e.getName(), date.format(DATE_FORMATTER),
                        e.getTimeInterval().getStartTime().toString(), e.getTimeInterval().getEndTime().toString()));
            }
        }
        Collections.sort(dayEvents);
        for (Event e : dayEvents) {
            dateAndEvents += formatter.formatEvent(e);
        }
        dateAndEvents += "\n";
        return dateAndEvents;
    }
}
