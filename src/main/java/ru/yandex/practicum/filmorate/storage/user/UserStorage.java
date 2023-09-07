package ru.yandex.practicum.filmorate.storage.user;

import ru.yandex.practicum.filmorate.model.User;

import java.util.List;

public interface UserStorage {
    User create(User user);

    List<User> findAll();

    User update(User film);

    User find(Long id);

    boolean delete(Long id);
}
