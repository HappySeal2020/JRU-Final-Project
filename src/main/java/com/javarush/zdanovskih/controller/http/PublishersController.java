package com.javarush.zdanovskih.controller.http;

import com.javarush.zdanovskih.entity.Publisher;
import com.javarush.zdanovskih.repository.PublisherRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


@Slf4j
@Controller

public class PublishersController {
    private final PublisherRepository publisherRepository;
    public PublishersController(PublisherRepository publisherRepository) {
        this.publisherRepository = publisherRepository;
    }

    @GetMapping("/publishers")
    public String publishers(Model model, @RequestParam(defaultValue = "0") int page,
                             @RequestParam(defaultValue = "5") int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Publisher> publishersPage = publisherRepository.findAll(pageable);
        model.addAttribute("publishersPage", publishersPage);
        model.addAttribute("currentPage", page);
        return "publishers";
    }

    @GetMapping("/publishers/put/{id}")
    public String put(@PathVariable Long id, Model model) {
        Publisher publisher = publisherRepository.findById(id).orElseThrow();
        model.addAttribute("publisher", publisher);
        log.info("Edit publisher: input id={}, found in db: id={} name={}",id, publisher.getId(), publisher.getName() );
        return "putPublisher";
    }

    @PutMapping("/publishers/put")
    public String put (@ModelAttribute Publisher publisher) {
        log.info("Try to put publisher id={} name={}...", publisher.getId(), publisher.getName());
        publisherRepository.save(publisher);
        return "redirect:/publishers";
    }
    @GetMapping("/publishers/del/{id}")
    public String del(@PathVariable long id, Model model) {
        Publisher publisher = publisherRepository.findById(id).orElseThrow();
        model.addAttribute("publisher", publisher);
        log.info("Del publisher: input id={}, found in db: id={} name={}",id, publisher.getId(), publisher.getName() );
        return "delPublisher";
    }

    @DeleteMapping("/publishers/del")
    public String removeAuthor(@ModelAttribute Publisher publisher) {
        log.info("Try to delete publisher id={} name={}...", publisher.getId(), publisher.getName());
        publisherRepository.deleteById(publisher.getId());
        return "redirect:/publishers";
    }

    @GetMapping("/publishers/add")
    public String add( Model model) {
        model.addAttribute("publisher", new Publisher());
        log.info("Add publisher" );
        return "addPublisher";
    }

    @PostMapping("/publishers/add")
    public String add(@ModelAttribute Publisher publisher) {
        log.info("Try to add publisher id={} name={}...", publisher.getId(), publisher.getName());
        //publisher.setId(0);
        publisherRepository.save(publisher);
        return "redirect:/publishers";
    }

}
