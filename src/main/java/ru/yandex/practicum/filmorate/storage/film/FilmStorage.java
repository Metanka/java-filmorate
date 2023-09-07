package ru.yandex.practicum.filmorate.storage.film;

import ru.yandex.practicum.filmorate.model.Film;

import java.util.List;

public interface FilmStorage {
    Film create(Film film);
    List<Film> findAll();
    Film update(Film film);
    Film find(Long id);
    boolean delete(Long id);
}
