package com.javarush.zdanovskih.entity;

import jakarta.persistence.*;
import lombok.*;

@Table(name = "user_tbl")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@ToString
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String login;
    private String email;
    private String password;

    @Enumerated(value = EnumType.STRING)
    private Role role;
    //private String role;

}
