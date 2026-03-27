package com.codewithmosh.store.users;

import com.codewithmosh.store.exceptions.InvalidAuthenticationCredentialsException;
import com.codewithmosh.store.mappers.UserMapper;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
@AllArgsConstructor
public class UserService implements UserDetailsService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        var user = userRepository.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException("User not found"));

        return new User(
                user.getEmail(),
                user.getPassword(),
                Collections.emptyList()
        );

    }

    //
    public com.codewithmosh.store.users.User getUserByEmail(String email) {
        return userRepository.findByEmail(email).orElseThrow(InvalidAuthenticationCredentialsException::new);
    }

    //
    public com.codewithmosh.store.users.User getUserById(Long userId) {
        return userRepository.findById(userId).orElseThrow(InvalidAuthenticationCredentialsException::new);

    }
}
