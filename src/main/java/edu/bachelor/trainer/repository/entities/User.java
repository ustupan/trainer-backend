package edu.bachelor.trainer.repository.entities;

import edu.bachelor.trainer.common.Gender;
import lombok.AllArgsConstructor;
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
    @ManyToMany
    @JoinTable(name = "user_role",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private Set<Role> roles;
    @Column
    @ManyToMany
    private Set<User> friends;
}
