package ru.yandex.practicum.filmorate.model;

import java.time.LocalDate;

import lombok.Data;
import lombok.AllArgsConstructor;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;


@Data
@AllArgsConstructor
public class Film {
    private int id;
    @NotBlank
    private String name;
    @Size(min = 1, max = 200)
    private String description;
    private LocalDate releaseDate;
    @Positive
    private int duration;
}
