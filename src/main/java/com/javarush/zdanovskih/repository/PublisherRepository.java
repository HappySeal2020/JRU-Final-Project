package com.javarush.zdanovskih.repository;

import com.javarush.zdanovskih.entity.Publisher;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PublisherRepository extends JpaRepository<Publisher, Long> {
}
