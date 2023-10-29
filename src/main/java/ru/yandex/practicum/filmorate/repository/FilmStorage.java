package ru.yandex.practicum.filmorate.repository;

import ru.yandex.practicum.filmorate.model.Film;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FilmStorage {
    private final Map<Integer, Film> films = new HashMap<>();


    public List<Film> getAllFilms() {
        return new ArrayList<>(films.values());
    }

    public void createNewFilm(Film film) {
        int id = getProperFilmId();
        film.setId(id);
        films.put(id, film);
    }

    public Film getFilmById(int id) {
        return films.get(id);
    }

    public void updateFilm(Film film) {
        Film filmToEdit = films.get(film.getId());
        filmToEdit.setName(film.getName());
        filmToEdit.setDescription(film.getDescription());
        filmToEdit.setReleaseDate(film.getReleaseDate());
        filmToEdit.setDuration(film.getDuration());
    }


    private int getProperFilmId() {
        if (films.isEmpty()) {
            return 1;
        }
        return Collections.max(films.keySet()) + 1;
    }
}
