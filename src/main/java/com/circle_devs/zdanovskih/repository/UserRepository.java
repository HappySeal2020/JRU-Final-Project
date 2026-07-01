package com.circle_devs.zdanovskih.repository;

import com.circle_devs.zdanovskih.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByLogin(String login);
}
