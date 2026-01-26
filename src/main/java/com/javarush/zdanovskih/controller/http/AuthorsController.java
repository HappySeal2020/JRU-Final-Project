package com.javarush.zdanovskih.controller.http;

import com.javarush.zdanovskih.entity.Author;
import com.javarush.zdanovskih.repository.AuthorRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
@Slf4j
@Controller
//@RequestMapping(WEB_MAP)
public class AuthorsController {
    private final AuthorRepository authorRepository;
    public AuthorsController(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    @GetMapping("/authors")
    public String authors(Model model, @RequestParam(defaultValue = "0") int page,
                             @RequestParam(defaultValue = "5") int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Author> authorsPage = authorRepository.findAll(pageable);
        model.addAttribute("authorsPage", authorsPage);
        model.addAttribute("currentPage", page);

        return "authors";
    }
    @GetMapping("/authors/put/{id}")
    public String putAuthor(@PathVariable long id, Model model) {
        Author author = authorRepository.findById(id).orElseThrow();
        model.addAttribute("author", author);
        log.info("Edit author: input id={}, found in db: id={} name={}",id, author.getId(), author.getName() );
        return "putAuthor";
    }

    @PutMapping("/authors/put")
    public String updAuthor(@ModelAttribute Author author) {
        log.info("Try to put author id={} name={}...", author.getId(), author.getName());
        authorRepository.save(author);
        return "redirect:/authors";
    }
    @GetMapping("/authors/del/{id}")
    public String delAuthor(@PathVariable long id, Model model) {
        Author author = authorRepository.findById(id).orElseThrow();
        model.addAttribute("author", author);
        log.info("Del author: input id={}, found in db: id={} name={}",id, author.getId(), author.getName() );
        return "delAuthor";
    }

    @DeleteMapping("/authors/del")
    public String removeAuthor(@ModelAttribute Author author) {
        log.info("Try to delete author id={} name={}...", author.getId(), author.getName());
        authorRepository.deleteById(author.getId());
        return "redirect:/authors";
    }

    @GetMapping("/authors/add")
    public String addAuthor( Model model) {
        model.addAttribute("author", new Author());
        log.info("Add author" );
        return "addAuthor";
    }

    @PostMapping("/authors/add")
    public String addAuthor(@ModelAttribute Author author) {
        log.info("Try to add author id={} name={}...", author.getId(), author.getName());
        author.setId(0);
        authorRepository.save(author);
        return "redirect:/authors";
    }

}
