package com.circle_devs.zdanovskih.repository;

import com.circle_devs.zdanovskih.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;


public interface BookRepository extends JpaRepository<Book, Long> , JpaSpecificationExecutor<Book> {
}
