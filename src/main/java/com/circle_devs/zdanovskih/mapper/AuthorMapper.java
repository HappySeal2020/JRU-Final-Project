package com.circle_devs.zdanovskih.mapper;

import com.circle_devs.zdanovskih.dto.AuthorResponseDto;
import com.circle_devs.zdanovskih.entity.Author;
import org.springframework.stereotype.Component;

/**
 * Mapper for Author
 */
@Component
public class AuthorMapper {
    //Author -> AuthorResponseDto
    public AuthorResponseDto authorToAuthorResponseDto(Author author) {
        return new AuthorResponseDto(author.getId(),
                author.getName());
    }


}
