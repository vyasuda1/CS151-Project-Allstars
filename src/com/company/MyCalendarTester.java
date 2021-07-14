package com.company;
/**
 * Contains a class with a main method that tests the MyCalendar Class.
 * @author Viola Yasuda
 * @version 1.0 6/14/21
 */
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

/**
 * MyCalendarTester contains a main method that tests the MyCalendar class.
 */
public class MyCalendarTester {
    /**
     * Produces a main menu that allows the user to interact with a calendar.
     * @param args any arguments passed during run time
     */
    public static void main(String [] args) {
        Scanner in = new Scanner(System.in);
        String mainMenuChoice = "";

        MyCalendar calendar = new MyCalendar();
        //show current month with today in brackets
        calendar.printInitialScreen();
        while (!mainMenuChoice.equals("Q")) {
            System.out.println("\nSelect one of the following main menu options:");
            System.out.print("[V]iew by\n[C]reate\n[G]o to\n[E]vent list\n[D]elete\n[Q]uit\n>");
            mainMenuChoice = in.next().toUpperCase();
            if (mainMenuChoice.equals("V")) {
                LocalDate dateToView = LocalDate.now();
                System.out.print("[D]ay view or [M]onth view?\n>");
                String vMenuChoice = in.next().toUpperCase();
                if (vMenuChoice.equals("D")) {
                    String dMenuChoice = "";
                    do {
                        //display today's date along with scheduled events in order of event start time
                        calendar.displayDayView(dateToView);
                        System.out.print("[P]revious or [N]ext or [G]o back to the main menu?\n>");
                        dMenuChoice = in.next().toUpperCase();
                        if (dMenuChoice.equals("P")) {
                            //set to show previous day
                            dateToView = dateToView.minusDays(1);
                        }
                        else if (dMenuChoice.equals("N")) {
                            //set to show next day
                            dateToView = dateToView.plusDays(1);
                        }
                    } while (dMenuChoice.equals("P") || dMenuChoice.equals("N"));
                    if (!dMenuChoice.equals("G")) {
                        System.out.println("Error: invalid input. Type a single letter to choose an option.");
                    }
                }
                else if (vMenuChoice.equals("M")) {
                    String mMenuChoice = "";
                    do {
                        //display current month and highlight days w/{} if events occur
                        calendar.displayMonthView(dateToView);
                        System.out.print("[P]revious or [N]ext or [G]o back to the main menu?\n>");
                        mMenuChoice = in.next().toUpperCase();
                        if (mMenuChoice.equals("P")) {
                            //set to show previous month
                            dateToView = dateToView.minusMonths(1);
                        }
                        else if (mMenuChoice.equals("N")){
                            //set to show next month
                            dateToView = dateToView.plusMonths(1);
                        }
                    } while (mMenuChoice.equals("P") || mMenuChoice.equals("N"));
                    if (!mMenuChoice.equals("G")) {
                        System.out.println("Error: invalid input. Type a single letter to choose an option.");
                    }
                }
                else {
                    System.out.println("Error: invalid input. Type a single letter to choose an option.");
                }
            }
            else if (mainMenuChoice.equals("C")) {
                in.nextLine(); //needed to avoid error when using next() and nextLine()
                System.out.print("Enter event name: ");
                String name = in.nextLine();
                System.out.print("Enter event date (format it MM/DD/YYYY): ");
                String date = in.next();
                System.out.print("Enter starting time in 24-hour military time (format it HH:MM): ");
                String startingTime = in.next();
                System.out.print("Enter ending time in 24-hour military time (format it HH:MM): ");
                String endingTime = in.next();
                calendar.addEvent(new Event(name, date, startingTime, endingTime));
            }
            else if (mainMenuChoice.equals("G")) {
                System.out.print("Enter a date (format it MM/DD/YYYY): ");
                String dateAsStr = in.next();
                //display the Day view of the requested date including events in the order of starting time.
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("M/d/yyyy");
                LocalDate date = LocalDate.parse(dateAsStr, formatter);
                calendar.displayDayView(date);
            }
            else if (mainMenuChoice.equals("E")) {
                calendar.printAllEvents();
            }
            else if (mainMenuChoice.equals("D")) {
                System.out.print("[S]elected  [A]ll   [DR]\n>");
                String dMenuChoice = in.next().toUpperCase();
                if (dMenuChoice.equals("S")) {
                    System.out.print("Enter the date (format it MM/DD/YYYY): ");
                    String date = in.next();
                    in.nextLine(); //needed to avoid error when using next() and nextLine()
                    //display all events scheduled on date
                    calendar.printOneTimeEvents(date);
                    System.out.print("Enter the name of the event to delete: ");
                    String name = in.nextLine();
                    calendar.deleteOneTimeEvent(name, date);
                }
                else if (dMenuChoice.equals("A")) {
                    System.out.print("Enter the date (format it MM/DD/YYYY): ");
                    String date = in.next();
                    //delete all one-time events on this date
                    calendar.deleteAllOneTimeEvents(date);
                }
                else if (dMenuChoice.equals("DR")) {
                    in.nextLine(); //needed to avoid error when using next() and nextLine()
                    System.out.print("Enter the name of the event to delete: ");
                    String name = in.nextLine();
                    //delete the specified recurring event
                    calendar.deleteRecurringEvent(name);
                }
                else {
                    System.out.println("Error: invalid input. Type a single letter to choose an option.");
                }
            }
            else if (mainMenuChoice.equals("Q")) {
                System.out.println("Goodbye!");
                //save current events in output.txt
                calendar.saveOutput();
            }
            else {
                System.out.println("Error: invalid input. Type a single letter to choose an option.");
            }
        }
    }
}
