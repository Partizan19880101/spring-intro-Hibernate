package com.epam.training.controller;

import com.epam.training.model.Event;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import com.epam.training.service.EventService;

import javax.validation.Valid;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import static com.epam.training.util.Constant.*;

@Slf4j
@Controller
@RequestMapping("/events")
public class EventController {

    @Autowired
    private EventService eventService;

    @GetMapping("/index")
    public String showEventList(Model model) {
        model.addAttribute("events", eventService.getAllEvents());
        return "events/index";
    }

    @GetMapping("/signup")
    public String showSignUpForm(@ModelAttribute("event") Event event) {
        return "events/add-event";
    }

    @PostMapping("/addevent")
    public String addEvent(@RequestParam("title") String title,
                           @RequestParam("date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date date) {

        eventService.create(new Event(title, date));
        return "redirect:/events/index";
    }


    @GetMapping("/edit/{id}")
    public String showUpdateForm(@PathVariable("id") long id, Model model) {
        Event event = eventService.getEventById(id);

        model.addAttribute("event", event);
        return "events/update-event";
    }


    @PostMapping("/update/{id}")
    public String updateEvent(@PathVariable("id") long id,
                              @RequestParam("title") String title,
                              @RequestParam("date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date date) {

        eventService.getEventById(id).setId(id);

        eventService.create(new Event(title, date));
        return "redirect:/events/index";
    }

    @GetMapping("/delete/{id}")
    public String deleteEvent(@PathVariable("id") long id, Model model) {
        eventService.delete(id);
        return "redirect:/events/index";
    }

    @GetMapping("/{id}")
    public ModelAndView getEventById(@PathVariable long id) {
        ModelAndView modelAndView = new ModelAndView("entities");
        Event event = eventService.getEventById(id);
        if (Objects.nonNull(event)) {
            modelAndView.addObject("entities", event);
        }
        modelAndView.addObject(MESSAGE, SUCCESSFUL_SEARCH_MESSAGE);
        return modelAndView;
    }

    @GetMapping("/title/{title}")
    public ModelAndView getEventsByTitle(@PathVariable String title,
                                         @RequestParam(required = false, defaultValue = "25") int pageSize,
                                         @RequestParam(required = false, defaultValue = "1") int pageNum) {
        ModelAndView modelAndView = new ModelAndView("entities");
        List<Event> events = eventService.getEventsByTitle(title, pageSize, pageNum);
        if (!events.isEmpty()) {
            modelAndView.addObject("entities", events);
            modelAndView.addObject(MESSAGE, SUCCESSFUL_SEARCH_MESSAGE);
        } else {
            modelAndView.addObject(MESSAGE, SUCCESSFUL_SEARCH_MESSAGE);
        }
        return modelAndView;
    }

    @GetMapping("/date/{date}")
    public ModelAndView getEventsByDate(@PathVariable String date,
                                        @RequestParam(required = false, defaultValue = "25") int pageSize,
                                        @RequestParam(required = false, defaultValue = "1") int pageNum) {
        ModelAndView modelAndView = new ModelAndView("entities");
        List<Event> events = eventService.getEventsForDay(parseDate(date), pageSize, pageNum);
        if (!events.isEmpty()) {
            modelAndView.addObject("entities", events);
            modelAndView.addObject(MESSAGE, SUCCESSFUL_SEARCH_MESSAGE);
        } else {
            modelAndView.addObject(MESSAGE, FAILED_SEARCH_MESSAGE);
        }
        return modelAndView;
    }

    private Date parseDate(String date) {
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
        try {
            return formatter.parse(date);
        } catch (Exception e) {
            return null;
        }
    }
}