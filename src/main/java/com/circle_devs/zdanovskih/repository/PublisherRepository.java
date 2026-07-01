package com.circle_devs.zdanovskih.repository;

import com.circle_devs.zdanovskih.entity.Publisher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Optional;

public interface PublisherRepository extends JpaRepository<Publisher, Long> , JpaSpecificationExecutor<Publisher> {
    Optional<Publisher> findByName(String name);
}
