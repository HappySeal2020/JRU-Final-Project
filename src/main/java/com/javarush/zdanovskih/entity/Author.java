package com.javarush.zdanovskih.entity;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
@Entity
@Table(name="author")
public class Author {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @NotNull
    @NotEmpty
    @Column(unique=true)
    private String name;

    public Author(String name) {
        this.name = name;
    }

    public Author() {

    }

}
