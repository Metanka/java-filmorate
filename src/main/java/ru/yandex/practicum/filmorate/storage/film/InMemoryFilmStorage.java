package ru.yandex.practicum.filmorate.storage.film;

import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exception.FilmNotFoundException;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class InMemoryFilmStorage implements FilmStorage {
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
            throw new FilmNotFoundException(film.getId());
        }
        films.put(film.getId(), film);
        return film;
    }

    @Override
    public Film find(Long id) {
        if (films.containsKey(id)) {
            return films.get(id);
        }
        throw new FilmNotFoundException(id);
    }

    @Override
    public boolean delete(Long id) {
        if (films.containsKey(id)) {
            films.remove(id);
            return true;
        }
        throw new FilmNotFoundException(id);
    }

    public boolean addLike(Long userId, Long filmId) {
        if (!films.containsKey(filmId)) {
            throw new FilmNotFoundException(filmId);
        }
        return films.get(filmId).addLike(userId);
    }

     public boolean deleteLike(Long userId, Long filmId) {
        if (!films.containsKey(filmId)) {
            throw new FilmNotFoundException(filmId);
        }
        return films.get(filmId).deleteLike(userId);
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
