package com.javarush.zdanovskih.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
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
    @NotEmpty(message="Book name is mandatory field")
    @NotNull(message="Book name is mandatory field")
    @Column(unique=true)
    private String name;
    @ManyToMany(cascade = CascadeType.MERGE)
    @JoinTable(name="author_to_book", joinColumns = @JoinColumn(name="book_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name="author_id", referencedColumnName = "id"))
    private List<Author> authors = new ArrayList<>();
    @NotInFutureYear
    @Min(value = 1900, message = "Print year must be greater 1900")
    private int printYear;
    @ManyToOne @JoinColumn (name="publisher_id")
    public Publisher publisher;
    private String bbk;
    private String isbn;
    @Min(value=1, message = "Число страниц должно быть положительным")
    private int pages;

    public Book(long id, String name, List<Author> author, int printYeari, Publisher publisher, String bbk, String isbn, int pages) {
        //
        this.id = id;
        this.name = name;
        this.authors = author;
        this.printYear = printYeari;
        this.publisher = publisher;
        this.bbk = bbk;
        this.isbn = isbn;
        this.pages = pages;
    }

    public Book() {

    }
}

