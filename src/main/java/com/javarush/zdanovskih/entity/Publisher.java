package com.javarush.zdanovskih.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "publisher")
public class Publisher {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String name;
    private String site;

    public Publisher(long id, String name, String site) {
        this.id = id;
        this.name = name;
        this.site = site;
    }

    public Publisher() {

    }
}

