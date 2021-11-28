package com.epam.training.repository;

import com.epam.training.model.Ticket;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface TicketRepo extends CrudRepository<Ticket, Long> {

    List<Ticket> findByUserId(long userId);

    List<Ticket> findByEventId(long eventId);
}
