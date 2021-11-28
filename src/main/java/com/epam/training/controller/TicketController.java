package com.epam.training.controller;

import com.epam.training.model.Event;
import com.epam.training.model.Ticket;
import com.epam.training.model.UserEntity;
import lombok.extern.slf4j.Slf4j;
import com.epam.training.model.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import com.epam.training.service.EventService;
import com.epam.training.service.TicketService;
import com.epam.training.service.UserService;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static com.epam.training.util.Constant.*;

@Slf4j
@Controller
@RequestMapping("/tickets")
public class TicketController {

    @Autowired
    private TicketService ticketService;
    private UserService userService;
    private EventService eventService;

    @PostMapping
    public ModelAndView bookTicket(@RequestParam long userId,
                                   @RequestParam long eventId,
                                   @RequestParam int place,
                                   @RequestParam Category category) {
        ModelAndView modelAndView = new ModelAndView("tickets/tickets");
        UserEntity user = userService.getUserById(userId);
        Event event = eventService.getEventById(eventId);
        if (Objects.nonNull(event) && Objects.nonNull(user)) {
            Ticket ticket = ticketService.bookTicket(user, event, place, category);
            modelAndView.addObject("tickets", ticket);
            modelAndView.addObject(MESSAGE, SUCCESSFUL_CREATION_MESSAGE);
        } else {
            modelAndView.addObject(MESSAGE, "Failed to book a ticket");
        }
        return modelAndView;
    }

    @GetMapping(value = "/{ticket}/{id}")
    public ModelAndView getBookedTickets(@PathVariable long id,
                                         @PathVariable String entity,
                                         @RequestParam(required = false, defaultValue = "100") int pageSize,
                                         @RequestParam(required = false, defaultValue = "1") int pageNum) {
        ModelAndView modelAndView = new ModelAndView("tickets");
        List<Ticket> tickets = getTickets(id, entity, pageSize, pageNum);
        if (!tickets.isEmpty()) {
            modelAndView.addObject("tickets", tickets);
            modelAndView.addObject(MESSAGE, SUCCESSFUL_SEARCH_MESSAGE);
        } else {
            modelAndView.addObject(MESSAGE, FAILED_SEARCH_MESSAGE);
        }
        return modelAndView;
    }

    @DeleteMapping("/{id}")
    public ModelAndView cancelTicket(@PathVariable long id) {
        ModelAndView modelAndView = new ModelAndView("tickets");
        boolean isDeleted = ticketService.cancelTicket(id);
        if (isDeleted) {
            modelAndView.addObject(MESSAGE, SUCCESSFUL_SEARCH_MESSAGE);
        } else {
            modelAndView.addObject(MESSAGE, FAILED_SEARCH_MESSAGE);
        }
        return modelAndView;
    }

    private List<Ticket> getTickets(long id, String entity, int pageSize, int pageNum) {
        List<Ticket> tickets = new ArrayList<>();
        if ("users".equalsIgnoreCase(entity)) {
            UserEntity user = userService.getUserById(id);
            tickets = ticketService.getBookedTickets(user, pageSize, pageNum);
        }
        if ("events".equalsIgnoreCase(entity)) {
            Event event = eventService.getEventById(id);
            tickets = ticketService.getBookedTickets(event, pageSize, pageNum);
        }
        return tickets;
    }
}