package ru.yandex.practicum.filmorate.service;

import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;

import java.time.LocalDate;
import java.util.*;

@Service
public class FilmService {
    private final Map<Long, Film> films = new HashMap<>();
    private Long id = 0L;

    public List<Film> findAll() {
        return new ArrayList<>(films.values());
    }

    public Film create(Film film) {
        if (checkValidation(film)) {
            film.setId(++id);
            films.put(film.getId(), film);
            return film;
        }
        throw new ValidationException("Фильм не прошел валидацию.");
    }

    public Film update(Film film) {
        if (films.get(film.getId()) == null) {
            throw new ValidationException("Такого фильма нет.");
        }
        films.put(film.getId(), film);
        return film;
    }

    private boolean checkValidation(Film film) {
        if (film.getDescription().length() > 200) {
            throw new ValidationException("Описание фильма превышает 200 символов");
        } else if (film.getReleaseDate().isBefore(LocalDate.of(1895, 12, 28))) {
            throw new ValidationException("В те года фильмов еще не было");
        } else {
            return true;
        }
    }
}
