package edu.bachelor.trainer.user.controllers.dtos;

import edu.bachelor.trainer.repository.entities.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Set;

@AllArgsConstructor
@Getter
@Setter
public class RoleDto {

    RoleDto() {

    }

    private Long id;

    private String Name;

}