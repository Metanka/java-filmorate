package ru.yandex.practicum.filmorate.service;

import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;

import java.time.LocalDate;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@Service
public class FilmService {
    private final Map<String, Film> films = new HashMap<>();
    private static Integer globalId = 0;

    public Collection<Film> findAllFilms() {
        return films.values();
    }

    private static Integer getNextId() {
        return globalId++;
    }

    public Film create(Film film) {
        if (checkValidation(film)) {
            film.setId(getNextId());
            films.put(film.getName(), film);
            return film;
        }
        throw new ValidationException("Фильм не прошел валидацию.");
    }

    public Film update(Film film) {
        return films.put(film.getName(), film);
    }

    private boolean checkValidation(Film film) {
        if (film.getName().isBlank()) {
            throw new ValidationException("Имя фильма не должно быть пустым");
        } else if (film.getDescription().length() > 200) {
            throw new ValidationException("Описание фильма превышает 200 символов");
        } else if (film.getReleaseDate().isBefore(LocalDate.of(1895, 12, 28))) {
            throw new ValidationException("В те года фильмов еще не было");
        } else if (film.getDuration() < 0) {
            throw new ValidationException("Фильм не может воспроизводиться в прошлое");
        } else return true;
    }
}
