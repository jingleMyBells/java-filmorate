package ru.yandex.practicum.filmorate.controller;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import lombok.extern.slf4j.Slf4j;

import ru.yandex.practicum.filmorate.exceptions.ValidationException;
import ru.yandex.practicum.filmorate.model.User;


@Slf4j
@RestController
public class UserController {
    private final Map<Integer, User> users = new HashMap<>();

    @GetMapping("/users")
    public List<User> getAll() {
        return new ArrayList<>(users.values());
    }

    @PostMapping(value = "/users")
    public ResponseEntity<User> create(@RequestBody User user) {
        try {
            validateEntity(user);
        }  catch (ValidationException e) {
            log.warn(e.getMessage());
            return new ResponseEntity<>(user, HttpStatus.BAD_REQUEST);
        }
        if (user.getName() == null || user.getName().isBlank()) {
            user.setName(user.getLogin());
        }
        int id = getProperId();
        user.setId(id);
        users.put(id, user);
        log.info("Пользователь успешно добавлен");
        return new ResponseEntity<>(user, HttpStatus.CREATED);
    }

    @PutMapping(value = "/users")
    public ResponseEntity<User> fullUpdate(@RequestBody User user) {
        try {
            validateEntity(user);
        }  catch (ValidationException e) {
            log.warn(e.getMessage());
            return new ResponseEntity<>(user, HttpStatus.BAD_REQUEST);
        }
        if (user.getName().isBlank()) {
            user.setName(user.getLogin());
        }
        int id;
        if (user.getId() == 0 || users.get(user.getId()) == null) {
//            id = getProperId();
            return new ResponseEntity<>(user, HttpStatus.NOT_FOUND);
        } else {
            id = user.getId();
            users.remove(id);
        }
        users.put(id, user);
        log.info("Пользователь успешно обновлен целиком");
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    private void validateEntity(User user) {
        if (user.getEmail().isBlank() || !user.getEmail().contains("@")) {
            throw new ValidationException("Email должен содержать символ @ и не быть пустым");
        }
        if (user.getLogin().isBlank() || user.getLogin().contains(" ")) {
            throw new ValidationException("Логин не должен быть пустым и содержать пробелы");
        }
        if (user.getBirthday().isAfter(LocalDate.now())) {
            throw new ValidationException("День рождения не может быть в будущем");
        }
    }

    private int getProperId() {
        if (users.isEmpty()) {
            return 1;
        }
        return Collections.max(this.users.keySet()) + 1;
    }
}
