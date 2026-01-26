package com.javarush.zdanovskih.dto;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.javarush.zdanovskih.entity.Author;
import com.javarush.zdanovskih.entity.Publisher;
import lombok.Data;

import java.util.List;

@Data
public class BookResponseDto {
    @JsonProperty("id")
    private final long id;
    @JsonProperty("name")
    private final String name;
    @JsonProperty("print_year")
    private final int printYear;
    @JsonProperty("bbk")
    private final String bbk;
    @JsonProperty("isbn")
    private final String isbn;
    @JsonProperty("pages")
    private final int pages;
    @JsonProperty("publisher")
    private final Publisher publisher;
    @JsonProperty("author")
    private final List<Author> author;

    /*
    public BookResponseDto(Book book, Publisher publisher, List<Author> author) {
        this.id = book.getId();
        this.name = book.getName();
        this.printYear = book.getPrintYear();
        this.bbk = book.getBbk();
        this.isbn = book.getIsbn();
        this.pages = book.getPages();
        this.publisher = publisher;
        this.author = author;
    }
*/
    public BookResponseDto(long id, String name, List<Author> authors, int printYear, Publisher publisher, String bbk, String isbn, int pages) {
        this.id = id;
        this.name = name;
        this.author = authors;
        this.printYear = printYear;
        this.bbk = bbk;
        this.isbn = isbn;
        this.pages = pages;
        this.publisher = publisher;
    }

    //public BookResponseDto()

}
