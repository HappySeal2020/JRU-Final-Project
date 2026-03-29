package com.javarush.zdanovskih.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
@Entity
@Table(name = "publisher")
public class Publisher {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @NotEmpty(message="Publisher name is mandatory field")
    @NotNull(message="Publisher name is mandatory field")
    @Column(unique = true)
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

