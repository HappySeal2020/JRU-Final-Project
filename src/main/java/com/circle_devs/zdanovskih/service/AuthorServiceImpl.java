package com.circle_devs.zdanovskih.service;

import com.circle_devs.zdanovskih.entity.Author;
import com.circle_devs.zdanovskih.repository.AuthorRepository;
import com.circle_devs.zdanovskih.service.impl.AuthorService;
import com.circle_devs.zdanovskih.specification.AuthorSpecification;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Service for managing Authors
 */
@Slf4j
@Getter
@Service
@AllArgsConstructor
@Transactional
public class AuthorServiceImpl implements AuthorService {
    private final AuthorRepository authorRepository;

    @Override
    public List<Author> getAuthors(int page, int size, String name) {
        Sort.sort(Author.class).by(Author::getId);
        Pageable pageable = PageRequest.of(page, size, Sort.by("id"));
        Page<Author> authorPage;
        authorPage = authorRepository.findAll(
                AuthorSpecification.filter(name),
                pageable);
       return authorPage.getContent();

    }

    @Override
    public List<Author> findAllByIds(List<Long> longs) {
        List<Author> authors = authorRepository.findAllById(longs);
        if (authors.isEmpty()) {
            log.error("author(s) not found: {}", longs);
        }
        return authors;
    }

    @Override
    @Transactional
    public Author saveAuthor(Author author) {
        return authorRepository.saveAndFlush(author);
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        authorRepository.deleteById(id);
    }
}
