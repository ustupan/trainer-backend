package edu.bachelor.trainer.user.services.imp;

import edu.bachelor.trainer.repository.UserRepository;
import edu.bachelor.trainer.repository.entities.Role;
import edu.bachelor.trainer.repository.entities.User;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service("userDetailsService")
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    public CustomUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) {

        final Optional<User> findUser = userRepository.findByUsername(username);
        if (!findUser.isPresent()) {
            throw new UsernameNotFoundException(username);
        }
        User user = findUser.get();
        List<SimpleGrantedAuthority> authorities = new ArrayList<>();
        user.getRoles().forEach(role -> {
            authorities.add(new SimpleGrantedAuthority(role.getName()));
        });

//        Optional<edu.bachelor.trainer.repository.entities.User> findUser = userRepository.findByUsername(username);
//        Optional<edu.bachelor.trainer.repository.entities.User> findUser1 = userRepository.findByUsername(username);
//
//        return userRepository
//                .findByUsername(username)
//                .map(user -> new User(
//                        user.getUsername(),
//                        user.getPassword(),
//                        Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER"))))
//                .orElseThrow(() -> new UsernameNotFoundException(username));

        return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(), authorities);
    }
}