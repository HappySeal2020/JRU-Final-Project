package com.javarush.zdanovskih.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Table(name = "book")
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String name;
    //private long author_id;
    @ManyToMany(cascade = CascadeType.MERGE)
    @JoinTable(name="author_to_book", joinColumns = @JoinColumn(name="book_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name="author_id", referencedColumnName = "id"))
    private List<Author> authors = new ArrayList<Author>();
    private int printYear;
    //private long publisher_id;
    @ManyToOne @JoinColumn (name="publisher_id")
    public Publisher publisher;
    private String bbk;
    private String isbn;
    private int pages;
}

