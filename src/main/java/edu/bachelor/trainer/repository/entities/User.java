package edu.bachelor.trainer.repository.entities;

import edu.bachelor.trainer.common.Gender;
import lombok.*;

import javax.persistence.*;
import java.util.Set;


@AllArgsConstructor
@Table (name="Users")
@Entity
@Getter
@Setter
public class User {

    public User(){
    }

    @Id
    @GeneratedValue (strategy = GenerationType.AUTO)
    @Column
    private Long id;
    @Column
    private String username;
    @Column
    private String email;
    @Column
    private String password;
    @Column
    private Gender gender;
    @Column
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "user_role",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private Set<Role> roles;


}
