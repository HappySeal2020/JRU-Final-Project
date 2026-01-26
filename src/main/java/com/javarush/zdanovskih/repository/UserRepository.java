package com.javarush.zdanovskih.repository;

import com.javarush.zdanovskih.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByLogin(String login);
}
