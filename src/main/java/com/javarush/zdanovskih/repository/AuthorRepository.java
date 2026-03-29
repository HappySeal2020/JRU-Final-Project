package com.javarush.zdanovskih.repository;

import com.javarush.zdanovskih.entity.Author;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface AuthorRepository extends JpaRepository<Author, Long> , JpaSpecificationExecutor<Author>
{

}