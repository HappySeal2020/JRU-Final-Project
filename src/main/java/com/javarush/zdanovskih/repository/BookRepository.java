package com.javarush.zdanovskih.repository;

import com.javarush.zdanovskih.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<Book, Long> {
}
