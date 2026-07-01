package com.circle_devs.zdanovskih.repository;

import com.circle_devs.zdanovskih.entity.Author;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Optional;

public interface AuthorRepository extends JpaRepository<Author, Long> , JpaSpecificationExecutor<Author>
{
    Optional<Author> findByName(String name);

}