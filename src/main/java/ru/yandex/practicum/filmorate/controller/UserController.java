package ru.yandex.practicum.filmorate.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import lombok.extern.slf4j.Slf4j;
import javax.validation.Valid;

import ru.yandex.practicum.filmorate.exceptions.ValidationException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.repository.UserStorage;


@Slf4j
@RestController
@RequestMapping("/users")
public class UserController {
    private final UserStorage storage = new UserStorage();

    @GetMapping
    public List<User> getAll() {
        return storage.getAllUsers();
    }

    @PostMapping
    public ResponseEntity<User> create(@Valid @RequestBody User user) {
        try {
            validateEntity(user);
        }  catch (ValidationException e) {
            log.warn(e.getMessage());
            return new ResponseEntity<>(user, HttpStatus.BAD_REQUEST);
        }
        if (user.getName() == null || user.getName().isBlank()) {
            user.setName(user.getLogin());
        }
        storage.createNewUser(user);
        log.info("Пользователь успешно добавлен");
        return new ResponseEntity<>(user, HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity<User> fullUpdate(@Valid @RequestBody User user) {
        try {
            validateEntity(user);
        }  catch (ValidationException e) {
            log.warn(e.getMessage());
            return new ResponseEntity<>(user, HttpStatus.BAD_REQUEST);
        }
        if (user.getName().isBlank()) {
            user.setName(user.getLogin());
        }
        if (user.getId() == 0 || storage.getUserById(user.getId()) == null) {
            return new ResponseEntity<>(user, HttpStatus.NOT_FOUND);
        }
        storage.updateUser(user);
        log.info("Пользователь успешно обновлен целиком");
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    private void validateEntity(User user) {
        if (user.getLogin().contains(" ")) {
            throw new ValidationException("Логин не должен содержать пробелы");
        }
    }
}
