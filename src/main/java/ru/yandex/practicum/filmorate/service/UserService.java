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
    private final Map<Integer, User> users = new HashMap<>();

    public Collection<User> findAllUser() {
        return users.values();
    }
    private static Integer id = 1;

    public User createUser(User user) {
        if (checkValidation(user)) {
            if (users.containsKey(user.getId())) {
                throw new UserAlreadyExistException("Пользователь с электронной почтой " +
                        user.getEmail() + " уже зарегистрирован.");
            }
            user.setId(id);
            id++;
            if (user.getName() == null) {
                user.setName(user.getLogin());
            }
            users.put(user.getId(), user);
            return user;
        }
        throw new ValidationException("Пользователь не прошел валидацию.");
    }

    public User updateUser(User user) {
        if (user.getEmail().isBlank()) {
            throw new InvalidEmailException("Адрес электронной почты не может быть пустым.");
        }
        if ( users.get(user.getId()) == null ) {
            throw new ValidationException("Id не совпадают.");
        }
        users.put(user.getId(), user);

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
