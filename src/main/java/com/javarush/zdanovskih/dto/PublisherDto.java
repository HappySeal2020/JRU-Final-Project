package com.javarush.zdanovskih.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class PublisherDto {
    @JsonProperty("id")
    private final long id;
    @NotEmpty(message = "Publisher name is mandatory field")
    @NotBlank(message = "Publisher name is mandatory field")
    @JsonProperty("name")
    private final String name;
    @JsonProperty("site")
    private final String site;

    public PublisherDto(long id, String name, String site) {
        this.id = id;
        this.name = name;
        this.site = site;
    }
}
