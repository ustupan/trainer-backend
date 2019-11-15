package edu.bachelor.trainer.security.registration.services.imp;

import edu.bachelor.trainer.repository.RoleRepository;
import edu.bachelor.trainer.repository.UserRepository;
import edu.bachelor.trainer.repository.entities.User;
import edu.bachelor.trainer.security.exceptions.UndefinedRoleException;
import edu.bachelor.trainer.security.exceptions.UserNameExistsException;
import edu.bachelor.trainer.security.registration.services.RegisterService;
import edu.bachelor.trainer.user.controllers.dtos.UserDto;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Arrays;
import java.util.Optional;

@Service
public class RegisterServiceImp implements RegisterService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    public RegisterServiceImp(UserRepository userRepository, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }

    @Transactional
    @Override
    public User registerNewUserAccount(UserDto accountDto)
            throws UserNameExistsException {

        if (usernameExist(accountDto.getUsername())) {
            throw new UserNameExistsException(
                    "There is an account with that username: "
                            +  accountDto.getUsername());
        }

        User user = new User();
        user.setUsername(accountDto.getUsername());
        user.setPassword(accountDto.getPassword());
        user.setEmail(accountDto.getEmail());
        user.setRoles(Arrays.asList(createRole("ROLE_USER"), createRole(accountDto.getRoleName())));

        return userRepository.save(user);
    }

    private boolean usernameExist(String username) {
        Optional<User> user = userRepository.findByUsername(username);
        return user.isPresent();
    }


    private edu.bachelor.trainer.repository.entities.Role createRole(String roleName) {
        Optional<edu.bachelor.trainer.repository.entities.Role> findRole = roleRepository.findByName(roleName);
        if (!findRole.isPresent()) {
            throw new UndefinedRoleException(roleName);
        }
        edu.bachelor.trainer.repository.entities.Role role = findRole.get();
        if (role.getName().contains("ADMIN")) {
            throw new UndefinedRoleException(roleName);
        }
        return role;
    }

}
