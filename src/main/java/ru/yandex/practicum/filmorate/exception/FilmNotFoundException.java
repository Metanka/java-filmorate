package ru.yandex.practicum.filmorate.exception;

public class FilmNotFoundException extends RuntimeException {

    private final Long parameter;

    public FilmNotFoundException(Long parameter) {
        this.parameter = parameter;
    }

    public String getParameter() {
        return parameter.toString();
    }
}