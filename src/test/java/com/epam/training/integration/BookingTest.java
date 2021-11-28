package com.epam.training.integration;

import com.epam.training.config.SpringConfig;
import com.epam.training.model.Category;
import com.epam.training.model.Event;
import com.epam.training.model.Ticket;
import com.epam.training.model.UserEntity;
import com.epam.training.service.EventService;
import com.epam.training.service.TicketService;
import com.epam.training.service.UserService;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import com.epam.training.service.impl.EventServiceImpl;
import com.epam.training.service.impl.TicketServiceImpl;
import com.epam.training.service.impl.UserServiceImpl;

import java.util.Date;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class BookingTest {

    private static UserService userService;
    private static TicketService ticketService;
    private static EventService eventService;
    private static UserEntity testUser;
    private static Event testEvent;
    private static Ticket testTicket;

    @BeforeClass
    public static void init() {
        ApplicationContext context = new AnnotationConfigApplicationContext(SpringConfig.class);
        userService = context.getBean(UserServiceImpl.class);
        ticketService = context.getBean(TicketServiceImpl.class);
        eventService = context.getBean(EventServiceImpl.class);
    }

    @Test
    public void createUser() {
        testUser = new UserEntity("ivan", "ivan@mail.ru");
        userService.createUser(testUser);
        assertThat(testUser).isEqualTo(userService.getUserById(testUser.getId()));
    }

    @Test
    public void createEvent() {
        testEvent = new Event("Swan Lake", new Date());
        eventService.create(testEvent);
        assertThat(testEvent.getTitle()).isEqualTo("Swan Lake");
    }

    @Test
    public void bookAndCancelTicket() {
        testTicket = ticketService.bookTicket(testUser, testEvent, 35, Category.BAR);
        assertThat(ticketService.getBookedTickets(testUser,1, 1)).isEqualTo(1);
        assertThat(ticketService.getBookedTickets(testEvent, 1,1)).isEqualTo(1);

        ticketService.cancelTicket(testTicket.getId());

        assertThat(ticketService.getBookedTickets(testUser, 1,1)).isEqualTo(0);
        assertThat(ticketService.getBookedTickets(testEvent, 1,1)).isEqualTo(0);
    }
}
