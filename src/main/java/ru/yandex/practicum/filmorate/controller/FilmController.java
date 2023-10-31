package ru.yandex.practicum.filmorate.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import javax.validation.Valid;
import lombok.extern.slf4j.Slf4j;

import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.repository.FilmStorage;


@Slf4j
@RestController
@RequestMapping("/films")
public class FilmController {
    private final FilmStorage storage;

    @Autowired
    public FilmController(FilmStorage storage) {
        this.storage = storage;
    }

    @GetMapping
    public List<Film> getAll() {
        return storage.getAllFilms();
    }

    @PostMapping
    public ResponseEntity<Film> create(@Valid @RequestBody Film film) {
        storage.createNewFilm(film);
        log.info("Фильм успешно добавлен");
        return new ResponseEntity<Film>(film, HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity<Film> fullUpdate(@Valid @RequestBody Film film) {
        if (film.getId() == 0  || storage.getFilmById(film.getId()) == null) {
            return new ResponseEntity<Film>(film, HttpStatus.NOT_FOUND);
        }
        storage.updateFilm(film);
        log.info("Фильм успешно обновлен полностью");
        return new ResponseEntity<Film>(film, HttpStatus.OK);
    }
}
