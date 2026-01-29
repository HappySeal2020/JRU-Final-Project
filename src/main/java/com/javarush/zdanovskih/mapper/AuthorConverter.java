package com.javarush.zdanovskih.mapper;

import com.javarush.zdanovskih.entity.Author;
import com.javarush.zdanovskih.repository.AuthorRepository;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class AuthorConverter implements Converter<String, Author> {
    private final AuthorRepository authorRepository;

    public AuthorConverter(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    @Override
    public Author convert(String source) {
        return authorRepository.findById(Long.parseLong(source)).orElse(null);
    }
}
