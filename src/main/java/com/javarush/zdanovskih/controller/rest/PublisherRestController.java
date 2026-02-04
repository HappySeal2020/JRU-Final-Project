package com.javarush.zdanovskih.controller.rest;

import com.javarush.zdanovskih.entity.Publisher;
import com.javarush.zdanovskih.repository.PublisherRepository;
import com.javarush.zdanovskih.specification.PublisherSpecification;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.InputMismatchException;
import java.util.List;

import static com.javarush.zdanovskih.constant.Const.REST_MAP;
import static com.javarush.zdanovskih.constant.Const.REST_PUBLISHER_PATH;

@Slf4j
@RestController
@RequestMapping(REST_MAP)
@Validated
public class PublisherRestController {
    private final PublisherRepository publisherRepository;


    public PublisherRestController(PublisherRepository publisherRepository) {
        this.publisherRepository = publisherRepository;
    }


    //Read
    /*
    @GetMapping(REST_PUBLISHER_PATH)
    @ResponseStatus(HttpStatus.OK)
    public List<Publisher> getAllPublishers() {
        return publisherRepository.findAll();
    }*/

    @GetMapping(REST_PUBLISHER_PATH)
    @ResponseStatus(HttpStatus.OK)
    public List<Publisher> getAllPublishers(@RequestParam(required = false) String name,
                                            @RequestParam(required = false) String site) {
        return publisherRepository.findAll(PublisherSpecification.filter(name, site));
    }

    //Create
    @PostMapping(REST_PUBLISHER_PATH)
    @ResponseStatus(HttpStatus.CREATED)
    public Publisher createPublisher(@Valid @RequestBody Publisher publisher) {
        log.info("REST API - Creating new publisher: {}", publisher);
        return publisherRepository.save(publisher);
    }

    //Update
    @PutMapping(REST_PUBLISHER_PATH+"/{id}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public Publisher updatePublisher(@PathVariable Long  id, @Valid @RequestBody Publisher publisher) {
        if(id.equals(publisher.getId())) {
            log.info("REST API - Updating publisher={}", publisher);
            return publisherRepository.save(publisher);
        }
        else {
            log.error("REST API - Update publisher with incorrect id={}", id);
            throw badRequest(new InputMismatchException("Incorrect id"));
        }
    }

    //Delete
    @DeleteMapping(REST_PUBLISHER_PATH+"/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletePublisher(@PathVariable Long id) {
        log.info("REST API - Deleting publisher={}", id);
        publisherRepository.deleteById(id);
    }

    private ResponseStatusException notFound(Publisher publisher) {
        return new ResponseStatusException(HttpStatus.NOT_FOUND, "Publisher not found");
    }
    private ResponseStatusException badRequest(Exception e) {
        return new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage(), e);
    }

}
