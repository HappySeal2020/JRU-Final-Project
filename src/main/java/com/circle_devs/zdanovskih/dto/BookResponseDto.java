package com.circle_devs.zdanovskih.dto;

import java.util.List;

public record BookResponseDto(long id,
                              String name,
                              List<AuthorResponseDto> authors,
                              int printYear,
                              PublisherResponseDto publisher,
                              String bbk,
                              String isbn,
                              int pages) {
}
