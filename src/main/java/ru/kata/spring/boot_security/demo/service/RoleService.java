package ru.kata.spring.boot_security.demo.service;

import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;

import java.util.List;
import java.util.Set;

public interface RoleService {

    List<Role> getAllRoles();
    List<Role> getUserRoles(Long userId);
    Role getRoleByName(String roleName);
    Set<Role> getAnyRoleById(List<Long> roles);
}