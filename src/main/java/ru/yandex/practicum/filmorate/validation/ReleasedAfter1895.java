package ru.yandex.practicum.filmorate.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Documented
@Constraint(validatedBy = FilmReleaseDateValidator.class)
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface ReleasedAfter1895 {

    String message() default "Дата релиза не может быть раньше первого кинопоказа в истории";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}
