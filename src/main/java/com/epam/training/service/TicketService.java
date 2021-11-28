package com.epam.training.service;

import com.epam.training.model.Category;
import com.epam.training.model.Event;
import com.epam.training.model.Ticket;
import com.epam.training.model.UserEntity;

import java.util.List;

/**
 * The interface Ticket service.
 */
public interface TicketService {

    /**
     * Book ticket for a specified event on behalf of specified user.
     *
     * @param user   User Id.
     * @param event  Event Id.
     * @param place    Place number.
     * @param category Service category.
     * @return Booked ticket object.
     * @throws IllegalStateException if this place has already been booked.
     */
    Ticket bookTicket(UserEntity user, Event event, int place, Category category);

    /**
     * Get all booked tickets for specified user. Tickets should be sorted by event date in descending order.
     *
     * @param user     User
     * @param pageSize Pagination param. Number of tickets to return on a page.
     * @param pageNum  Pagination param. Number of the page to return. Starts from 1.
     * @return List of Ticket objects.
     */
    List<Ticket> getBookedTickets(UserEntity user, int pageSize, int pageNum);

    /**
     * Get all booked tickets for specified event. Tickets should be sorted in by user email in ascending order.
     *
     * @param event    Event
     * @param pageSize Pagination param. Number of tickets to return on a page.
     * @param pageNum  Pagination param. Number of the page to return. Starts from 1.
     * @return List of Ticket objects.
     */
    List<Ticket> getBookedTickets(Event event, int pageSize, int pageNum);

    /**
     * Cancel ticket with a specified id.
     *
     * @param ticketId Ticket id.
     * @return Flag whether anything has been canceled.
     */
    boolean cancelTicket(long ticketId);
}
