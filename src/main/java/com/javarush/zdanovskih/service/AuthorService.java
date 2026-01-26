package com.javarush.zdanovskih.service;

import com.javarush.zdanovskih.entity.Author;
import com.javarush.zdanovskih.repository.AuthorRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
@Transactional
public class AuthorService {
    private final AuthorRepository authorRepository;

    public List<Author> getAllAuthors() {
        Sort sort = Sort.sort(Author.class).by(Author::getId);
        return authorRepository.findAll(sort);
    }

    @Transactional
    public void deleteById(Long id) {
        authorRepository.deleteById(id);
    }

    @Transactional
    public Author save (Author author) {
        return authorRepository.saveAndFlush(author);
    }
}
