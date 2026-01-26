package com.javarush.zdanovskih.entity;


import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name="author")
public class Author {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String name;

    public Author(String name) {
        this.name = name;
    }

    public Author() {

    }

}
