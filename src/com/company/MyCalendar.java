package com.company;
/**
 * Contains a class thar represents a calendar.
 * @author Viola Yasuda
 * @version 6/14/21
 */
import java.io.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

/**
 * MyCalendar represents a calendar and defines an underlying data structure to hold events.
 */
public class MyCalendar {
    private ArrayList<Event> events;
    private String[][] month;

    public static final String DAYS_OF_WEEK[] = new String[]{"Sun ", "Mon ", "Tue ", "Wed ", "Thu ", "Fri ", "Sat\n"};
    public static final DateTimeFormatter FILE_FORMATTER = DateTimeFormatter.ofPattern("M/d/yy"); //for dates from file
    public static final DateTimeFormatter USER_FORMATTER = DateTimeFormatter.ofPattern("MM/dd/yyyy"); //for dates from user

    /**
     * Constructs a MyCalendar object from the contents of events.txt.
     */
    public MyCalendar() {
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
     * Deletes a one-time event of a specified name.
     * @param eventName the name of the event to be deleted
     * @param dateAsStr the date of the event to be deleted
     * precondition: dateAsStr must be in the format MM/DD/YY
     */
    public void deleteOneTimeEvent(String eventName, String dateAsStr) {
        LocalDate date = LocalDate.parse(dateAsStr, USER_FORMATTER);
        for (Event e : events) {
            if (!e.isRecurring() && e.getName().equals(eventName) && e.getDates().contains(date)) {
                events.remove(e);
                return;
            }
        }
        System.out.println("Error: no one-time event by that name was found.");
    }

    /**
     * Deletes all one-time events occurring on a specified date.
     * @param dateAsStr the date to delete the one-time events occurring on it
     * precondition: dateAsStr must be in the format MM/DD/YY
     */
    public void deleteAllOneTimeEvents(String dateAsStr) {
        LocalDate date = LocalDate.parse(dateAsStr, USER_FORMATTER);
        boolean deletedAtLeastOneEvent = false;
        for (int i = 0; i < events.size(); i++) {
            if (events.get(i).getDates().contains(date) && !events.get(i).isRecurring()) {
                events.remove(i);
                deletedAtLeastOneEvent = true;
                i--;
            }
        }
        if (!deletedAtLeastOneEvent)
            System.out.println("Error: no one-time events occurring on the specified date were found.");
    }

    /**
     * Deletes a recurring event of a specified name.
     * @param eventName the name of the event to be deleted
     */
    public void deleteRecurringEvent(String eventName) {
        for (Event e : events) {
            if (e.isRecurring() && e.getName().equals(eventName)) {
                events.remove(e);
                return;
            }
        }
        System.out.println("Error: no recurring event by that name was found.");
    }

    /**
     * Shows the current month and highlights today's date using a pair of brackets.
     */
    public void printInitialScreen() {
        LocalDate today = LocalDate.now();
        populateMonth(today);

        boolean found = false;
        for (int i = 0; i < month.length; i++) {
            for (int j = 0; j < month[0].length; j++) {
                if (month[i][j].contains("" + today.getDayOfMonth())) {
                    month[i][j] = "[" + today.getDayOfMonth() + "]";
                    if (today.getDayOfMonth() < 10)
                        month[i][j] += " ";
                    found = true;
                    break;
                }
            }
            if (found)
                break;
        }

        System.out.println(today.getMonth() + " " + today.getYear());
        printMonth();
    }

    /**
     * Prints all one-time events in order of date and starting time, then all recurring events in order of date.
     */
    public void printAllEvents() {
        String oneTime = "", recurring = "";
        for (Event e : events)
            if (e.isRecurring())
                recurring += e.toString() + "\n";
            else
                oneTime += e.toString() + "\n";
        System.out.println("One-Time Events:\n" + oneTime);
        System.out.println("Recurring Events:\n" + recurring);
    }

    /**
     * Prints all one-time events occurring on a specified date.
     * @param dateAsStr the date to be viewed
     * precondition: dateAsStr is in the format MM/DD/YY
     */
    public void printOneTimeEvents(String dateAsStr) {
        LocalDate date = LocalDate.parse(dateAsStr, USER_FORMATTER);
        for (Event e : events)
            if (e.getDates().contains(date) && !e.isRecurring())
                System.out.println(e.getName() + ": " + e.getTimeInterval().toString());
    }

    /**
     * Displays a date along with scheduled events in order of event start time.
     * @param date the date to be displayed
     */
    public void displayDayView(LocalDate date) {
        ArrayList<Event> dayEvents = new ArrayList<>();
        System.out.println(date.getDayOfWeek() + ", " + date.getMonth() + " " + date.getDayOfMonth() + ", " +
                date.getYear());
        for (Event e : events) {
            if (e.getDates().contains(date)) {
                dayEvents.add(new Event(e.getName(), date.format(USER_FORMATTER),
                        e.getTimeInterval().getStartTime().toString(), e.getTimeInterval().getEndTime().toString()));
            }
        }
        Collections.sort(dayEvents);
        for (Event e : dayEvents) {
            System.out.println(e.getName() + ": " + e.getTimeInterval().toString());
        }
    }

    /**
     * Displays a date's month and highlights days with {} if events occur.
     * @param date the date whose month is to be displayed
     */
    public void displayMonthView(LocalDate date) {
        populateMonth(date);
        ArrayList<Integer> daysWithEvents = new ArrayList<>();
        for (Event e : events)
            for (LocalDate d : e.getDates())
                if (d.getMonth().equals(date.getMonth())) daysWithEvents.add(d.getDayOfMonth());
        for (int d : daysWithEvents) {
            boolean found = false;
            for (int i = 0; i < month.length; i++) {
                for (int j = 0; j < month[0].length; j++)
                    if (month[i][j].contains("" + d)) {
                        month[i][j] = "{" + d + "}";
                        if (d < 10) month[i][j] += " ";
                        found = true;
                        break;
                    }
                if (found) break;
            }
        }

        System.out.println(date.getMonth() + " " + date.getYear());
        printMonth();
    }

    /**
     * Saves current events in output.txt.
     */
    public void saveOutput() {
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
     * Populates the month array given a date.
     * @param date a date
     */
    private void populateMonth(LocalDate date) {
        month = new String[6][7];
        LocalDate firstDay = LocalDate.of(date.getYear(), date.getMonth(), 1);
        int day = 1;
        for (int i = 0; i < month.length; i++) {
            for (int j = 0; j < month[0].length; j++) {
                if (((j < firstDay.getDayOfWeek().getValue() && day == 1) &&
                        (firstDay.getDayOfWeek().getValue() != 7)) || day > firstDay.lengthOfMonth()) {
                    month[i][j] = "    ";
                }
                else if (day < 10) {
                    month[i][j] = " " + day + "  ";
                    day++;
                }
                else {
                    month[i][j] = " " + day + " ";
                    day++;
                }
            }
        }
    }

    /**
     * Prints the month.
     */
    private void printMonth() {
        for (String s : DAYS_OF_WEEK) System.out.print(s);
        for (int i = 0; i < month.length; i++) {
            for (int j = 0; j < month[0].length; j++) System.out.print(month[i][j]);
            System.out.println();
        }
    }
}
