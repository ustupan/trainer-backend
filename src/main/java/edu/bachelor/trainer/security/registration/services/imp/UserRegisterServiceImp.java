package edu.bachelor.trainer.security.registration.services.imp;

import edu.bachelor.trainer.repository.RoleRepository;
import edu.bachelor.trainer.repository.UserRepository;
import edu.bachelor.trainer.repository.entities.User;
import edu.bachelor.trainer.security.exceptions.UndefinedRoleException;
import edu.bachelor.trainer.security.exceptions.UsernameExistsException;
import edu.bachelor.trainer.security.registration.services.UserRegisterService;
import edu.bachelor.trainer.user.controllers.dtos.UserDto;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Optional;

@Service
public class UserRegisterServiceImp implements UserRegisterService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    public UserRegisterServiceImp(UserRepository userRepository, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }

    @Override
    public User registerNewUserAccount(UserDto accountDto)
            throws UsernameExistsException, UndefinedRoleException {

        if (usernameExist(accountDto.getUsername())) {
            throw new UsernameExistsException(
                    "There is an account with that username: "
                            +  accountDto.getUsername());
        }

        String encoded = new BCryptPasswordEncoder().encode(accountDto.getPassword());

        User user = new User();
        user.setUsername(accountDto.getUsername());
        user.setPassword(encoded);
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
