package com.javarush.zdanovskih.controller.rest;

import com.javarush.zdanovskih.entity.Publisher;
import com.javarush.zdanovskih.repository.PublisherRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.InputMismatchException;
import java.util.List;

import static com.javarush.zdanovskih.constant.Const.REST_MAP;
import static com.javarush.zdanovskih.constant.Const.REST_PUBLISHER_PATH;

@Slf4j
@RestController
@RequestMapping(REST_MAP)
public class PublisherRestController {
    private final PublisherRepository publisherRepository;
    //private final PublisherService publisherService;
    //private final String PUBLISHER_PATH="/publishers";

    public PublisherRestController(PublisherRepository publisherRepository) {
        this.publisherRepository = publisherRepository;
    }
    //public PublisherController(PublisherService publisherService) {
    //    this.publisherService = publisherService;
    //}

    //Read
    @GetMapping(REST_PUBLISHER_PATH)
    @ResponseStatus(HttpStatus.OK)
    public List<Publisher> getAllPublishers() {
        return publisherRepository.findAll();
        // .getAllPublishers();
    }

    //Create
    @PostMapping(REST_PUBLISHER_PATH)
    @ResponseStatus(HttpStatus.CREATED)
    public Publisher createPublisher(@RequestBody Publisher publisher) {
        try{
            return publisherRepository.save(publisher);
        } catch (Exception e){
            log.error("Create publisher={}", publisher, e);
            throw badRequest(e);
        }
    }

    //Update
    @PutMapping(REST_PUBLISHER_PATH+"/{id}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public Publisher updatePublisher(@PathVariable Long  id, @RequestBody Publisher publisher) {
        if(id.equals(publisher.getId())) {
            log.info("Updating publisher={}", publisher);
            return publisherRepository.save(publisher);
        }
        else {
            log.error("Update publisher with incorrect id={}", id);
            throw badRequest(new InputMismatchException("Incorrect id"));
        }
    }

    //Delete
    @DeleteMapping(REST_PUBLISHER_PATH+"/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletePublisher(@PathVariable Long id) {
        try{
            publisherRepository.deleteById(id);
        } catch (Exception e){
            log.error("Delete publisher id={}", id, e);
            throw badRequest(e);
        }
    }

    private ResponseStatusException notFound(Publisher publisher) {
        return new ResponseStatusException(HttpStatus.NOT_FOUND, "Publisher not found");
    }
    private ResponseStatusException badRequest(Exception e) {
        return new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage(), e);
    }

}
