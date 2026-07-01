package com.circle_devs.zdanovskih.facade;

import com.circle_devs.zdanovskih.dto.AuthorCreateDto;
import com.circle_devs.zdanovskih.dto.AuthorResponseDto;
import com.circle_devs.zdanovskih.dto.AuthorUpdateDto;
import com.circle_devs.zdanovskih.entity.Author;
import com.circle_devs.zdanovskih.facade.impl.AuthorFacade;
import com.circle_devs.zdanovskih.service.AuthorServiceImpl;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * facade for Author uses services and returns DTO
 */
@Component
public class AuthorFacadeImpl implements AuthorFacade {
    private final AuthorServiceImpl authorService;
    public AuthorFacadeImpl(final AuthorServiceImpl authorService) {
        this.authorService = authorService;
    }

    /**
     *  create new author
     * @param dto - AuthorCreateDto
     * @return AuthorResponseDto (with id)
     */
    @Override
    public AuthorResponseDto create(AuthorCreateDto dto) {
        Author author = new Author();
        author.setName(dto.name());
        Author saved = authorService.saveAuthor(author);
        return new AuthorResponseDto(saved.getId(), saved.getName());
    }

    //read

    /**
     * get author with filter and pagination
     * @param page - page number (default 0)
     * @param size - page size (default 5)
     * @param name - filter author by name
     * @return List of AuthorResponseDto
     */
    @Override
    public List<AuthorResponseDto> getAllAuthors(
            int page,
            int size,
            String name) {
        return authorService.getAuthors(page, size, name)
                .stream()
                .map(author -> new AuthorResponseDto(author.getId(), author.getName()))
                .toList();
    }

    /**
     * Update author
     * @param id - author's id
     * @param dto - dto for update
     * @return dto after update
     */
    @Override
    public AuthorUpdateDto update(Long id, AuthorUpdateDto dto) {
        Author author = new Author();
        author.setId(id);
        author.setName(dto.name());
        Author saved= authorService.saveAuthor(author);
        return new AuthorUpdateDto(saved.getName());
    }

    /**
     * Delete author
     * @param id - author's id
     */
    @Override
    public void delete(Long id) {
        authorService.deleteById(id);
    }

}
