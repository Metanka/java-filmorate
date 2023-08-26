package ru.yandex.practicum.filmorate.service;

import lombok.NoArgsConstructor;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.InvalidEmailException;
import ru.yandex.practicum.filmorate.exception.UserAlreadyExistException;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.User;

import java.time.LocalDate;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@NoArgsConstructor
@Service
public class UserService {
    private final Map<String, User> users = new HashMap<>();

    public Collection<User> findAllUser() {
        return users.values();
    }
    public User createUser(User user) {
        if (checkValidation(user)) {
            if (users.containsKey(user.getEmail())) {
                throw new UserAlreadyExistException("Пользователь с электронной почтой " +
                        user.getEmail() + " уже зарегистрирован.");
            }
            users.put(user.getEmail(), user);
            return user;
        }
        throw new ValidationException("Пользователь не прошел валидацию.");
    }

    public User updateUser(User user) {
        if (user.getEmail().isBlank()) {
            throw new InvalidEmailException("Адрес электронной почты не может быть пустым.");
        }
        users.put(user.getEmail(), user);

        return user;
    }

    private boolean checkValidation(User user) {
        if (user.getEmail().isBlank() || !user.getEmail().contains("@")) {
            throw new ValidationException("Проверьте правильность email");
        } else if (user.getLogin().isBlank()) {
            throw new ValidationException("Логин не может быть пустым");
        } else if (user.getBirthday().isAfter(LocalDate.now())) {
            throw new ValidationException("Пользователь должен быть рожденым");
        } else return true;
    }

}
