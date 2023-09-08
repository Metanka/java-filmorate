package ru.yandex.practicum.filmorate.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.user.InMemoryUserStorage;

import java.time.LocalDate;
import java.util.List;

@Service
public class UserService {
    private final InMemoryUserStorage userStorage;

    @Autowired
    public UserService(InMemoryUserStorage userStorage) {
        this.userStorage = userStorage;
    }

    public List<User> findAll() {
        return userStorage.findAll();
    }

    public User create(User user) {
        if (checkValidation(user)) {
            return userStorage.create(user);
        }
        throw new ValidationException("Пользователь не прошел валидацию.");
    }

    public User update(User user) {
        return userStorage.update(user);
    }

    public boolean delete(Long id) {
        return userStorage.delete(id);
    }

    public boolean addFriend(Long userId, Long friendId) {
        return userStorage.addFriend(userId, friendId);
    }

    public boolean deleteFriend(Long userId, Long friendId) {
        return userStorage.deleteFriend(userId, friendId);
    }

    public List<User> getAllFriends(Long id) {
        return userStorage.getAllFriends(id);
    }

    public User find(Long id) {
        return userStorage.find(id);
    }

    public List<User> commonFriendsList(Long id, Long otherId) {
        return userStorage.commonFriendsList(id, otherId);
    }

    private boolean checkValidation(User user) {
        if (!user.getEmail().contains("@")) {
            throw new ValidationException("Проверьте правильность email");
        } else if (user.getBirthday().isAfter(LocalDate.now())) {
            throw new ValidationException("Пользователь должен быть рожденым");
        } else {
            return true;
        }
    }
}
