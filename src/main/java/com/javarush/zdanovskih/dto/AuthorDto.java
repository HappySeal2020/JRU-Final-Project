package com.javarush.zdanovskih.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class AuthorDto {
    @JsonProperty("id")
    private final long id;
    @JsonProperty("name")
    @NotEmpty(message = "Author name is mandatory field")
    @NotBlank(message = "Author name is mandatory field")
    private final String name;

    public AuthorDto(long id, String name) {
        this.name = name;
        this.id = id;
    }

}
