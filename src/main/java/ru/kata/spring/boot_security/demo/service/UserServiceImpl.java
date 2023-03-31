package ru.kata.spring.boot_security.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.dao.RoleDao;
import ru.kata.spring.boot_security.demo.dao.UserDao;
import ru.kata.spring.boot_security.demo.model.User;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    private final UserDao userDao;
    private final RoleDao roleDao;
    private final PasswordEncoder passEncoder;

    @Autowired
    public UserServiceImpl(UserDao userDao, RoleDao roleDao, PasswordEncoder passEncoder) {
        this.userDao = userDao;
        this.roleDao = roleDao;
        this.passEncoder = passEncoder;
    }

    private User encryptPassword(User user) {
        user.setPassword(passEncoder.encode(user.getPassword()));
        return user;
    }

    @Override
    @Transactional
    public void saveUser(User user) {
        userDao.saveUser(encryptPassword(user));
    }

    @Override
    @Transactional
    public void updateUser(User updateUser) {
        userDao.updateUser(encryptPassword(updateUser));
    }

    @Override
    @Transactional
    public void removeUserById(Long id) {
        userDao.removeUserById(id);
    }

    @Override
    public List<User> getAllUsers() {
        return userDao.getAllUsers();
    }

    @Override
    public User getUserById(Long id) {
        return userDao.getUserById(id);
    }

    @Override
    public User getUserByLogin(String login) {
        return userDao.getUserByLogin(login);
    }
}
