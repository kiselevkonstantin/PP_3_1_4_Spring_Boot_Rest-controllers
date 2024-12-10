package ru.kata.spring.boot_rest.demo.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_rest.demo.models.Role;
import ru.kata.spring.boot_rest.demo.models.User;
import ru.kata.spring.boot_rest.demo.repository.UserRepository;

import java.util.List;

@Service
@Transactional
public class UserServiceImpl implements UserService {


    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final ApplicationContext context;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, ApplicationContext context, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.context = context;
        this.passwordEncoder = passwordEncoder;
    }


    @Override
    @Transactional(readOnly = true)
    public List<User> findAll() {
        return userRepository.findAll();
    }


    @Override
    @Transactional(readOnly = true)
    public User getUserById(Long id) {
        return userRepository.getById(id);
    }


    @Override
    public void saveUser(User user) {
        userRepository.save(user);
    }

    @Override
    public void update(long id, User updatedUser, List<Role> roles) throws NullPointerException {
        User localUser = userRepository.getById(id);

        if (!localUser.getPassword().equals(updatedUser.getPassword())) {
            localUser.setPassword(passwordEncoder.encode(updatedUser.getPassword()));
        }
        localUser.setUsername(updatedUser.getUsername());
        localUser.setEmail(updatedUser.getEmail());
        localUser.setRoles(roles);

    }

    @Override
    public void delete(Long id) {
        userRepository.deleteById(id);
    }

    @Transactional(readOnly = true)
    @Override
    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }


    @Override
    public void setEncryptedPassword(User user) {
        PasswordEncoder passwordEncoder = context.getBean(PasswordEncoder.class);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
    }
}
