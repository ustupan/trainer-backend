package edu.bachelor.trainer.entity;

import lombok.AllArgsConstructor;
import lombok.Generated;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Set;


@AllArgsConstructor
@Table (name="Users")
@Entity
@Getter
@Setter
public class User {

    @Id
    @GeneratedValue (strategy = GenerationType.AUTO)
    @Column
    private Long id;
    @Column
    private String login;
    @Column
    private String email;
    @Column
    private String password;
    @Column
    private Gender gender;
    @Column
    private Role role;
    @Column
    @ManyToMany
    private Set<User> friends;
}
