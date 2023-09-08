package ru.yandex.practicum.filmorate.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.UserNotFoundException;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.storage.film.InMemoryFilmStorage;
import ru.yandex.practicum.filmorate.storage.user.InMemoryUserStorage;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class FilmService {
    private final InMemoryFilmStorage filmStorage;
    private final InMemoryUserStorage userStorage;

    @Autowired
    public FilmService(InMemoryFilmStorage filmStorage, InMemoryUserStorage userStorage) {
        this.filmStorage = filmStorage;
        this.userStorage = userStorage;
    }

    public List<Film> findAll() {
        return filmStorage.findAll();
    }

    public Film create(Film film) {
        if (checkValidation(film)) {
            return filmStorage.create(film);
        }
        throw new ValidationException("Фильм не прошел валидацию.");
    }

    public boolean delete(Long id) {
        return filmStorage.delete(id);
    }

    public Film find(Long id) {
        return filmStorage.find(id);
    }

    public Film update(Film film) {
        return filmStorage.update(film);
    }

    public boolean addLike(Long userId, Long filmId) {
        if (userStorage.find(userId) != null) {
            return filmStorage.addLike(userId, filmId);
        }
        throw new UserNotFoundException(userId);
    }

    public boolean deleteLike(Long userId, Long filmId) {
        if (userStorage.find(userId) != null) {
            return filmStorage.deleteLike(userId, filmId);
        }
        throw new UserNotFoundException(userId);
    }

    private int compare(Film p0, Film p1) {
        return p1.getLikes().size() - p0.getLikes().size();
    }

    public List<Film> getTopFilms(int count) {
        return filmStorage.findAll()
                .stream()
                .sorted(this::compare)
                .limit(count)
                .collect(Collectors.toList());
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
