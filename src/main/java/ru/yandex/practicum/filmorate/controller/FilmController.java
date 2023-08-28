package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import ru.yandex.practicum.filmorate.service.FilmService;

import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.Film;

import java.util.Collection;

@Slf4j
@RestController
public class FilmController {
    private final FilmService filmService;

    public FilmController() {
        filmService = new FilmService();
    }

    @GetMapping("/films")
    public Collection<Film> getFilms() {
        log.debug("Текущее количество фильмов: {}", filmService.findAllFilms().size());
        return filmService.findAllFilms();
    }

    @PostMapping(value = "/films")
    public Film create(@RequestBody Film film) {
        log.debug("Получен POST-запрос /film: " + film);
        return filmService.create(film);
    }

    @PutMapping(value = "/films")
    public Film updateFilm(@RequestBody Film film) {
        log.debug("Получен PUT-запрос /film: " + film);
        return filmService.update(film);
    }
}
