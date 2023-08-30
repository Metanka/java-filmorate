package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.service.FilmService;

import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.Film;

import javax.validation.Valid;
import java.util.List;

@Slf4j
@RestController
public class FilmController {
    private final FilmService filmService;

    public FilmController() {
        filmService = new FilmService();
    }

    @GetMapping("/films")
    public List<Film> getFilms() {
        log.debug("Пришел GET запрос /films");
        List<Film> response = filmService.findAll();
        log.debug("Отправлен ответ GET /films с телом: {}", response);
        return response;
    }

    @PostMapping(value = "/films")
    public Film create(@RequestBody @Valid Film film) {
        log.debug("Получен POST-запрос /films: " + film);
        Film response = filmService.create(film);
        log.debug("Отправлен ответ: " + response);
        return response;
    }

    @PutMapping(value = "/films")
    public Film updateFilm(@RequestBody @Valid Film film) {
        log.debug("Получен PUT-запрос /films: " + film);
        Film response = filmService.update(film);
        log.debug("Отправлен ответ: " + response);
        return response;
    }
}
