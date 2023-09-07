package ru.yandex.practicum.filmorate.service;

import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.user.InMemoryUserStorage;

import java.util.List;

@Service
public class UserService {
    private final InMemoryUserStorage userStorage;

    public UserService(InMemoryUserStorage userStorage) {
        this.userStorage = userStorage;
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
}
