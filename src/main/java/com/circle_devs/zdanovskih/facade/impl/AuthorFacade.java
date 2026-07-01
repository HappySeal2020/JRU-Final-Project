package com.circle_devs.zdanovskih.facade.impl;

import com.circle_devs.zdanovskih.dto.AuthorCreateDto;
import com.circle_devs.zdanovskih.dto.AuthorResponseDto;
import com.circle_devs.zdanovskih.dto.AuthorUpdateDto;

import java.util.List;

/**
 * Interface for AuthorFacade
 */
public interface AuthorFacade {
    AuthorResponseDto create(AuthorCreateDto dto);

    List<AuthorResponseDto> getAllAuthors(
            int page,
            int size,
            String name);

    AuthorUpdateDto update(Long id, AuthorUpdateDto dto);

    void delete(Long id);
}
