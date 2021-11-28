package com.epam.training.service.impl;

import com.epam.training.model.Event;
import com.epam.training.repository.EventRepo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import com.epam.training.service.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * The type Event service.
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class EventServiceImpl implements EventService {

    @Autowired
    private final EventRepo eventRepo;

    @Override
    public List<Event> getAllEvents() {
        log.info("Getting all events");
        return (List<Event>) eventRepo.findAll();
    }

    @Override
    public Event getEventById(long eventId) {
        log.info("Attempting to get event by id {}", eventId);
        return eventRepo.findById(eventId).orElse(null);
    }

    @Override
    public List<Event> getEventsByTitle(String title, int pageSize, int pageNum) {
        log.info("Attempting to get all events by title {}", title);
        return eventRepo.findByTitle(title);
    }

    @Override
    public List<Event> getEventsForDay(Date day, int pageSize, int pageNum) {
        log.info("Attempting to get all the events for day {}", day.toString());
        return eventRepo.findByDate(day);
    }

    @Override
    public Event create(Event event) {
        log.info("Attempting to create new event with name {}", event.getTitle());
        return eventRepo.save(event);
    }

    @Override
    public Event updateEvent(Event oldEvent, Event newEvent) {
        log.info("Attempting to update event with id {}", oldEvent.getId());
        eventRepo.deleteById(oldEvent.getId());
        Event addedEvent = eventRepo.save(newEvent);
        addedEvent.setId(oldEvent.getId());
        return addedEvent;
    }

    @Override
    public boolean delete(long eventId) {
        log.info("Attempting to delete event with id {}", eventId);
        eventRepo.deleteById(eventId);
        return true;
    }
}
