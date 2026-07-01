package com.circle_devs.zdanovskih.dto;

import java.util.List;

public record BookCreateDto(String name,
                            List<Long> authorIds,
                            int printYear,
                            Long publisherId,
                            String bbk,
                            String isbn,
                            int pages) {
}
