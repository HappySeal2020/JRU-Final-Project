package com.javarush.zdanovskih.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class PublisherDto {
    @JsonProperty("id")
    private final long id;
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
