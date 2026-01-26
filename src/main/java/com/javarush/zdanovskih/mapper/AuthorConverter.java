package com.javarush.zdanovskih.mapper;

import com.javarush.zdanovskih.entity.Author;
import com.javarush.zdanovskih.repository.AuthorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class AuthorConverter implements Converter<String, Author> {
    @Autowired
    private AuthorRepository authorRepository;

    @Override
    public Author convert(String source) {
        return authorRepository.findById(Long.parseLong(source)).orElse(null);
    }
}
