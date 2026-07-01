package com.circle_devs.zdanovskih.service.impl;

import com.circle_devs.zdanovskih.entity.Author;

import java.util.List;

/**
 * Interface for Authors service
 */
public interface AuthorService {

    List<Author> findAllByIds(List<Long> longs);
    List<Author> getAuthors(int page, int size, String name);

    Author saveAuthor(Author author);

    void deleteById(Long id);

}
