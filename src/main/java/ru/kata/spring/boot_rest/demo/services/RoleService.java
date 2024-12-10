package ru.kata.spring.boot_rest.demo.services;

import ru.kata.spring.boot_rest.demo.models.Role;

import java.util.List;

public interface RoleService {
    Role getRoleById(Long id);

    void save(Role role);

    void delete(Long id);

    List<Role> getDemandedRoles();
}
