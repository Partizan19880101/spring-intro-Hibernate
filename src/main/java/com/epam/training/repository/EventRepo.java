package com.epam.training.repository;

import com.epam.training.model.Event;
import org.springframework.data.repository.CrudRepository;

import java.util.Date;
import java.util.List;

public interface EventRepo extends CrudRepository<Event, Long> {

    List<Event> findByTitle(String title);

    List<Event> findByDate(Date day);
}
