package com.company;

public class NameFirstFormatter implements EventFormatter{
    @Override
    public String formatEvent(Event event) {
        return event.getName() + ": " + event.getTimeInterval().toString() + "\n";
    }
}
