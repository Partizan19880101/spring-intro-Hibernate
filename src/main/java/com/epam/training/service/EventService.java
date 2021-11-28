package com.epam.training.service;

import com.epam.training.model.Event;

import java.util.Date;
import java.util.List;

/**
 * The interface Event service.
 */
public interface EventService {

    List<Event> getAllEvents();

    Event getEventById(long eventId);

    List<Event> getEventsByTitle(String title, int pageSize, int pageNum);

    List<Event> getEventsForDay(Date day, int pageSize, int pageNum);

    Event create(Event event);

    Event updateEvent(Event oldEvent, Event newEvent);

    boolean delete(long eventId);
}
