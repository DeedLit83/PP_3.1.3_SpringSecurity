package ru.kata.spring.boot_security.demo.dao;

import org.hibernate.exception.ConstraintViolationException;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.TransactionException;
import org.springframework.transaction.UnexpectedRollbackException;
import ru.kata.spring.boot_security.demo.model.User;

import javax.persistence.*;

import java.util.List;

@Repository
public class UserDaoImpl implements UserDao{

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public void saveUser(User user) {
        entityManager.persist(user);
    }

    @Override
    public void updateUser(User updateUser) {
        entityManager.merge(updateUser);
    }

    @Override
    public void removeUserById(Long id) {
        User user = entityManager.find(User.class, id);
        if (user != null) {
            entityManager.remove(user);
        }
    }

    @Override
    public List<User> getAllUsers() {
        return entityManager.createQuery("SELECT user FROM User user", User.class).getResultList();
    }

    @Override
    public User getUserById(Long id) {
        return entityManager.find(User.class, id);
    }

    @Override
    public User getUserByLogin(String userName) {
        User user = null;
        try {
            TypedQuery<User> q = (entityManager.createQuery("SELECT u FROM User u " +
                    "JOIN FETCH u.roles WHERE u.login = :username", User.class));
            q.setParameter("username", userName);
            user = q.getSingleResult();
        } catch (NoResultException e) {
        }
        return user;
    }
}

