package com.epam.training.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import com.epam.training.model.Category;
import lombok.NoArgsConstructor;

import javax.persistence.*;

/**
 * The type Ticket.
 */
@Data
@Entity
@Table(name = "tickets", schema = "public")
@NoArgsConstructor
@AllArgsConstructor
public class Ticket {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private long eventId;
    private long userId;
    private Category category;
    private int place;

    public Ticket(long eventId, long userId, Category category, int place) {
        this.eventId = eventId;
        this.userId = userId;
        this.category = category;
        this.place = place;
    }
}
