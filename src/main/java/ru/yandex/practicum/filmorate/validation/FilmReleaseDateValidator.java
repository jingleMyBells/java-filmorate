package ru.yandex.practicum.filmorate.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.time.LocalDate;

import ru.yandex.practicum.filmorate.model.Film;


public class FilmReleaseDateValidator implements ConstraintValidator<ReleasedAfter1895, Film> {


    @Override
    public void initialize(ReleasedAfter1895 releasedAfter1895) {
    }

    @Override
    public boolean isValid(Film film, ConstraintValidatorContext constraintValidatorContext) {
        constraintValidatorContext.disableDefaultConstraintViolation();
        constraintValidatorContext.buildConstraintViolationWithTemplate(
                        "{ru.yandex.practicum.filmorate.validation.correctdate.releasedate}")
                .addPropertyNode("releaseDate").addConstraintViolation();
        return film.getReleaseDate().isAfter(LocalDate.of(1895, 12, 28));
    }
}
