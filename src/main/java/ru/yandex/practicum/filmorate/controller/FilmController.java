package ru.yandex.practicum.filmorate.controller;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;
import java.util.Map;
import java.util.Collections;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import lombok.extern.slf4j.Slf4j;

import ru.yandex.practicum.filmorate.exceptions.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;


@Slf4j
@RestController
public class FilmController {
    private final Map<Integer, Film> films = new HashMap<>();

    @GetMapping("/films")
    public List<Film> getAll() {
        return new ArrayList<>(films.values());
    }

    @PostMapping(value = "/films")
    public ResponseEntity<Film> create(@RequestBody Film film) {
        try {
            validateEntity(film);
        } catch (ValidationException e) {
            log.warn(e.getMessage());
            return new ResponseEntity<>(film, HttpStatus.BAD_REQUEST);
        }
        int id = getProperId();
        film.setId(id);
        films.put(id, film);
        log.info("Фильм успешно добавлен");
        return new ResponseEntity<>(film, HttpStatus.CREATED);
    }

    @PutMapping(value = "/films")
    public ResponseEntity<Film> fullUpdate(@RequestBody Film film) {
        try {
            validateEntity(film);
        } catch (ValidationException e) {
            log.warn(e.getMessage());
            return new ResponseEntity<>(film, HttpStatus.BAD_REQUEST);
        }
        int id;
        if (film.getId() == 0  || films.get(film.getId()) == null) {
//            id = getProperId();
            return new ResponseEntity<>(film, HttpStatus.NOT_FOUND);
        } else {
            id = film.getId();
        }
        films.put(id, film);
        log.info("Фильм успешно обновлен полностью");
        return new ResponseEntity<>(film, HttpStatus.OK);
    }

    private void validateEntity(Film film) {
        if (film.getName().isBlank()) {
            throw new ValidationException("Название фильма не может быть пустым");
        }
        if (film.getDescription().length() >= 200) {
            throw new ValidationException("Максимальная длина описания - 200 символов");
        }
        if (film.getReleaseDate().isBefore(LocalDate.of(1895, 12, 28))) {
            throw new ValidationException(
                    "Дата релиза не может быть раньше первого кинопоказа в истории"
            );
        }
        if (film.getDuration() <= 0) {
            throw new ValidationException("Продолжительность фильма должна быть положительной");
        }
    }

    private int getProperId() {
        if (films.isEmpty()) {
            return 1;
        }
        return Collections.max(this.films.keySet()) + 1;
    }
}
