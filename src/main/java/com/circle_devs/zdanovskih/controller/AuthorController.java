package com.circle_devs.zdanovskih.controller;

import com.circle_devs.zdanovskih.dto.AuthorCreateDto;
import com.circle_devs.zdanovskih.dto.AuthorResponseDto;
import com.circle_devs.zdanovskih.dto.AuthorUpdateDto;
import com.circle_devs.zdanovskih.facade.AuthorFacadeImpl;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import java.util.List;

import static com.circle_devs.zdanovskih.constant.Const.REST_AUTHOR_PATH;
import static com.circle_devs.zdanovskih.constant.Const.REST_MAP;

/**
 * Controller for Authors
 * Provides CRUD operations with book's author.
 */
@Slf4j
@RestController
@RequestMapping(REST_MAP)
@Validated
public class AuthorController {

    private final AuthorFacadeImpl authorFacadeImpl;

    public AuthorController(AuthorFacadeImpl authorFacadeImpl) {
        this.authorFacadeImpl = authorFacadeImpl;
    }

    @Operation(summary = "Чтение всех авторов. Фильтр по автору, пагинация")
    @GetMapping(REST_AUTHOR_PATH)
    @ResponseStatus(HttpStatus.OK)
    public List<AuthorResponseDto> getAuthors(@RequestParam(required = false) String name, //author name
                                      @RequestParam(defaultValue = "0") int page, //page number
                                      @RequestParam(defaultValue = "5") int size //page size
    ) {
        return authorFacadeImpl.getAllAuthors(page, size, name);
    }

    //Create
    @Operation(summary = "Создание нового автора")
    @PostMapping(REST_AUTHOR_PATH)
    @ResponseStatus(HttpStatus.CREATED)
    public AuthorResponseDto createAuthor (@Valid @RequestBody AuthorCreateDto authorCreateDto) {
        log.info("Creating author: {}", authorCreateDto);
        return authorFacadeImpl.create(authorCreateDto);
    }

    //Update
    @Operation(summary = "Изменение автора с номером {id}")
    @PutMapping(REST_AUTHOR_PATH+"/{id}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public AuthorUpdateDto updateAuthor (@PathVariable Long id, @Valid @RequestBody AuthorUpdateDto authorUpdateDto) {
        log.info("Updating author: {}, {}", id, authorUpdateDto);
        return authorFacadeImpl.update(id, authorUpdateDto);
    }

    //Delete
    @Operation(summary = "Удаление автора с номером {id}")
    @DeleteMapping(REST_AUTHOR_PATH+"/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteAuthor(@PathVariable Long id) {
        log.info("Deleting author: {}", id);
        authorFacadeImpl.delete(id);
    }

}
