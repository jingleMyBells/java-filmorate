package ru.yandex.practicum.filmorate.model;

import java.time.LocalDate;

import lombok.Data;
import lombok.AllArgsConstructor;


@Data
@AllArgsConstructor
public class Film {
    private int id;
    private String name;
    private String description;
    private LocalDate releaseDate;
    private int duration;
}
