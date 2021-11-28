package com.epam.training.service.impl;


import com.epam.training.model.Event;
import com.epam.training.model.Ticket;
import com.epam.training.model.UserEntity;
import com.epam.training.repository.TicketRepo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import com.epam.training.service.TicketService;
import com.epam.training.model.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * The type Ticket service.
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class TicketServiceImpl implements TicketService {

    @Autowired
    private final TicketRepo ticketRepo;

    @Override
    public Ticket bookTicket(UserEntity user, Event event, int place, Category category) {
        log.info("Attempting to book new ticket for {} event for user with id {}", user.getId(), event.getId());
        return ticketRepo.save(new Ticket(event.getId(), user.getId(), category, place));
    }

    @Override
    public List<Ticket> getBookedTickets(UserEntity user, int pageSize, int pageNum) {
        log.info("Attempting to get all tickets of user with id {}", user.getId());
        return ticketRepo.findByUserId(user.getId());
    }

    @Override
    public List<Ticket> getBookedTickets(Event event, int pageSize, int pageNum) {
        log.info("Attempting to get all booked tickets for event {}", event);
        return ticketRepo.findByEventId(event.getId());
    }

    @Override
    public boolean cancelTicket(long ticketId) {
        log.info("Attempting to cancel ticket with id {}", ticketId);
        ticketRepo.deleteById(ticketId);
        return true;
    }
}
