package com.javarush.zdanovskih.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class AuthorDto {
    @JsonProperty("id")
    private final long id;
    @JsonProperty("name")
    private final String name;

    public AuthorDto(long id, String name) {
        this.name = name;
        this.id = id;
    }

}
