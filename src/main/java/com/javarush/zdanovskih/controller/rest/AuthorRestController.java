package com.javarush.zdanovskih.controller.rest;

import com.javarush.zdanovskih.dto.AuthorDto;
import com.javarush.zdanovskih.entity.Author;
import com.javarush.zdanovskih.mapper.Mapper;
import com.javarush.zdanovskih.repository.AuthorRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;


import java.util.InputMismatchException;
import java.util.List;
import java.util.stream.Collectors;

import static com.javarush.zdanovskih.constant.Const.REST_AUTHOR_PATH;
import static com.javarush.zdanovskih.constant.Const.REST_MAP;

@Slf4j
@RestController
@RequestMapping(REST_MAP)
public class AuthorRestController {

    private final AuthorRepository authorRepository;

    public AuthorRestController(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    //Read
    @GetMapping(REST_AUTHOR_PATH)
    @ResponseStatus(HttpStatus.OK)
    public List<AuthorDto> getAllAuthors() {
        return authorRepository.findAll().stream().map(Mapper::toAuthorDto).collect(Collectors.toList());
    }

    //Create
    @PostMapping(REST_AUTHOR_PATH)
    @ResponseStatus(HttpStatus.CREATED)
    public AuthorDto createAuthor (@RequestBody AuthorDto author) {
        try{
            return Mapper.toAuthorDto(authorRepository.save(Mapper.toAuthor(author)));
        } catch (Exception e){
            log.error("Create author={}", author, e);
            throw badRequest(e);
        }
    }

    //Update
    @PutMapping(REST_AUTHOR_PATH+"/{id}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public Author updateAuthor (@PathVariable Long id, @RequestBody Author author) {
        if(id.equals(author.getId())) {
            return authorRepository.save(author);
        }
        else {
            log.error("Update author with incorrect id={}", id);
            throw badRequest(new InputMismatchException("Incorrect id"));
        }
    }

    //Delete
    @DeleteMapping(REST_AUTHOR_PATH+"/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletePublisher(@PathVariable Long id) {
        try{
            authorRepository.deleteById(id);
        } catch (Exception e){
            log.error("Delete author id={}", id, e);
            throw badRequest(e);
        }
    }

    private ResponseStatusException notFound(Author author) {
        return new ResponseStatusException(HttpStatus.NOT_FOUND, "Author not found");
    }
    private ResponseStatusException badRequest(Exception e) {
        return new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage(), e);
    }
}
