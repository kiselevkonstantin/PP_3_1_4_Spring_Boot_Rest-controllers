package ru.kata.spring.boot_rest.demo.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_rest.demo.models.Role;
import ru.kata.spring.boot_rest.demo.repository.RoleRepository;

import java.util.List;

@Transactional
@Service
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;

    @Autowired
    public RoleServiceImpl(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Transactional(readOnly = true)
    @Override
    public Role getRoleById(Long id) {
        return roleRepository.getById(id);
    }

    @Override
    public void save(Role role) {
        roleRepository.save(role);
    }

    @Override
    public void delete(Long id) {
        roleRepository.delete(roleRepository.getById(id));
    }

    @Transactional(readOnly = true)
    @Override
    public List<Role> getDemandedRoles() {
        return roleRepository.findAll();
    }
}
