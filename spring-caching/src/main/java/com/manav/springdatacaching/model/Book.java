package com.manav.springdatacaching.model;

import javax.persistence.Entity;
import javax.persistence.Id;

import java.io.Serializable;
import java.util.UUID;

@Entity
public class Book implements Serializable {

    @Id
    private UUID id;
    private String title;

    public Book(UUID id, String title) {
        this.id = id;
        this.title = title;
    }

    public Book() {

    }
}
