package ru.kata.spring.boot_security.demo.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.UserService;


@Service
@Primary
@Transactional
public class UserDetailServiceImpl implements UserDetailsService {

    private UserService userService;

    @Autowired
    public UserDetailServiceImpl(UserService userService) {
        this.userService = userService;
    }

    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        User user = userService.getUserByLogin(login);
        if (user == null) {
            throw new UsernameNotFoundException(String.format("User: %s not found.", login));
        }
        return new org.springframework.security.core.userdetails.User(user.getLogin(),
                user.getPassword(),
                user.getAuthorities());
    }
}
