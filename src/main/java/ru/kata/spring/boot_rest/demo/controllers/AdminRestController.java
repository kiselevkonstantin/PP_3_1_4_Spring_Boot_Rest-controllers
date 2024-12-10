package ru.kata.spring.boot_rest.demo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_rest.demo.exception_handling.NoSuchUserException;
import ru.kata.spring.boot_rest.demo.exception_handling.UserIncorrectData;
import ru.kata.spring.boot_rest.demo.models.User;
import ru.kata.spring.boot_rest.demo.services.UserService;

import java.util.List;

@RestController
@RequestMapping("/api")
public class AdminRestController {

    private final UserService userService;

    @Autowired
    public AdminRestController(UserService userService) {
        this.userService = userService;
    }

    // СПИСОК Users
    @GetMapping("/users")
    public List<User> showAllUsers() {
        return userService.findAll();
    }

    // ДОБАВИТЬ/СОЗДАТЬ User
    @PostMapping("/users")
    public ResponseEntity<HttpStatus> createUser(@RequestBody User user) {
        userService.saveUser(user);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    // ОБНОВИТЬ User
    @PatchMapping("/users")
    public ResponseEntity<HttpStatus> updateUser(@RequestBody User user) {
        userService.update(user.getId(), user, user.getRoles());
        return new ResponseEntity<>(HttpStatus.OK);
    }

    // УДАЛИТЬ User
    @DeleteMapping("/users/{id}")
    public ResponseEntity<HttpStatus> deleteUser(@PathVariable("id") Long id) {
        User user = userService.getUserById(id);
        if (user == null) {
            throw new NoSuchUserException("User with user_id " + id + " wasn't found in database");
        }
        userService.delete(id);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @ExceptionHandler
    public ResponseEntity<UserIncorrectData> handleException(NoSuchUserException exception) {
        UserIncorrectData data = new UserIncorrectData();
        data.setInfo(exception.getMessage());
        return new ResponseEntity<>(data, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler
    public ResponseEntity<UserIncorrectData> handleException(Exception exception) {
        UserIncorrectData data = new UserIncorrectData();
        data.setInfo(exception.getMessage());
        return new ResponseEntity<>(data, HttpStatus.BAD_REQUEST);
    }
}
