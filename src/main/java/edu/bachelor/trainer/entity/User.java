package edu.bachelor.trainer.entity;

import java.util.Set;

public class User {

    private Long id;
    private String login;
    private String email;
    private String password;
    private Gender gender;
    private Role role;
    private Set<User> friends;
}
