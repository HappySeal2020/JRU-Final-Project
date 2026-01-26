package com.javarush.zdanovskih.repository;

import com.javarush.zdanovskih.entity.Author;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthorRepository extends JpaRepository<Author, Long> {
}
