package com.javarush.zdanovskih.controller.rest;

import com.javarush.zdanovskih.dto.AuthorDto;
import com.javarush.zdanovskih.entity.Author;
import com.javarush.zdanovskih.mapper.Mapper;
import com.javarush.zdanovskih.repository.AuthorRepository;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;


import java.util.Comparator;
import java.util.InputMismatchException;
import java.util.List;
import java.util.stream.Collectors;

import static com.javarush.zdanovskih.constant.Const.REST_AUTHOR_PATH;
import static com.javarush.zdanovskih.constant.Const.REST_MAP;

@Slf4j
@RestController
@RequestMapping(REST_MAP)
@Validated
public class AuthorRestController {

    private final AuthorRepository authorRepository;

    public AuthorRestController(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    //Read
    @GetMapping(REST_AUTHOR_PATH)
    @ResponseStatus(HttpStatus.OK)
    public List<AuthorDto> getAllAuthors() {
        log.info("REST API - getAllAuthors");
        return authorRepository.findAll().stream().map(Mapper::toAuthorDto).sorted(Comparator.comparing(AuthorDto::getId)).collect(Collectors.toList());
    }

    //Create
    @PostMapping(REST_AUTHOR_PATH)
    @ResponseStatus(HttpStatus.CREATED)
    public AuthorDto createAuthor (@Valid @RequestBody AuthorDto author) {
        log.info("REST API - Creating author: {}", author);
        return Mapper.toAuthorDto(authorRepository.save(Mapper.toAuthor(author)));
    }

    //Update
    @PutMapping(REST_AUTHOR_PATH+"/{id}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public Author updateAuthor (@PathVariable Long id, @Valid @RequestBody Author author) {
        if(id.equals(author.getId())) {
            log.info("REST API - Updating author: {}", author);
            return authorRepository.save(author);
        }
        else {
            log.error("REST API - Update author with incorrect id={} failed", id);
            throw badRequest(new InputMismatchException("Incorrect id"));
        }
    }

    //Delete
    @DeleteMapping(REST_AUTHOR_PATH+"/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteAuthor(@PathVariable Long id) {
        log.info("REST API - Deleting author: {}", id);
        authorRepository.deleteById(id);
    }

    private ResponseStatusException badRequest(Exception e) {
        return new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage(), e);
    }
}
