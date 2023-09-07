package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;

import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exception.UserNotFoundException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.service.FilmService;
import ru.yandex.practicum.filmorate.storage.film.InMemoryFilmStorage;

import javax.validation.Valid;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/films")
public class FilmController {
    private final InMemoryFilmStorage filmStorage;
    private final FilmService filmService;

    public FilmController(InMemoryFilmStorage filmStorage) {
        this.filmStorage = filmStorage;
        this.filmService = new FilmService(filmStorage);
    }

    @GetMapping
    public List<Film> getFilms() {
        log.debug("Пришел GET запрос /films");
        List<Film> response = filmStorage.findAll();
        log.debug("Отправлен ответ GET /films с телом: {}", response);
        return response;
    }

    @PostMapping
    public Film create(@RequestBody @Valid Film film) {
        log.debug("Получен POST-запрос /films: " + film);
        Film response = filmStorage.create(film);
        log.debug("Отправлен ответ: " + response);
        return response;
    }

    @PutMapping
    public Film updateFilm(@RequestBody @Valid Film film) {
        log.debug("Получен PUT-запрос /films: " + film);
        Film response = filmStorage.update(film);
        log.debug("Отправлен ответ: " + response);
        return response;
    }

    @GetMapping("{id}")
    @ResponseBody
    public Film getFilmById(@PathVariable Long id) {
        Film response = filmStorage.find(id);
        log.debug("Отправлен ответ: " + response);
        return response;
    }

    @PutMapping("/{id}/like/{userId}")
    @ResponseBody
    public String addLike(@PathVariable Long id, @PathVariable Long userId) {
        log.debug("Добавление лайка с id фильма: " + id + ", id user: " + userId);
        boolean isSuccess = filmService.addLike(userId, id);
        String response = isSuccess ? "Лайк успешно добавлен!" : "Лайк уже добавлен.";
        log.debug("Отправлен ответ: " + response);
        return response;
    }

    @DeleteMapping("/{id}/like/{userId}")
    @ResponseBody
    public String deleteLike(@PathVariable Long id, @PathVariable Long userId) {
        log.debug("Удаление лайка с id фильма: " + id + ", id user: " + userId);
        boolean isSuccess = filmService.deleteLike(userId, id);
        String response = isSuccess ? "Лайк успешно удален!" : "Лайк уже удален.";
        log.debug("Отправлен ответ: " + response);
        return response;
    }

    @GetMapping("/popular")
    public List<Film> getPopularFilms(@RequestParam(defaultValue = "10") Integer count) {
        log.debug("Получение популярных фильмов: " + count + " штук");
        List<Film> response = filmService.getTopFilms(count);
        log.debug("Отправлен ответ: " + response);
        return response;
    }
}
