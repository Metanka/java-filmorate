package ru.yandex.practicum.filmorate.exception;

public class UserNotFoundException extends RuntimeException {
    private final Long parameter;

    public UserNotFoundException(Long parameter) {
        this.parameter = parameter;
    }

    public String getParameter() {
        return parameter.toString();
    }
}
