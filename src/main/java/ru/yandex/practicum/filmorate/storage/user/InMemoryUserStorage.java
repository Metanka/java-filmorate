package ru.yandex.practicum.filmorate.storage.user;

import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exception.InvalidEmailException;
import ru.yandex.practicum.filmorate.exception.UserAlreadyExistException;
import ru.yandex.practicum.filmorate.exception.UserNotFoundException;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.User;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Component
public class InMemoryUserStorage implements UserStorage {
    private final Map<Long, User> users = new HashMap<>();
    private Long id = 0L;

    public List<User> findAll() {
        return new ArrayList<>(users.values());
    }

    public User create(User user) {
        if (checkValidation(user)) {
            if (users.containsKey(user.getId())) {
                throw new UserAlreadyExistException("Пользователь с электронной почтой " +
                        user.getEmail() + " уже зарегистрирован.");
            }
            user.setId(++id);
            if (user.getName() == null || user.getName().isBlank()) {
                user.setName(user.getLogin());
            }
            users.put(user.getId(), user);
            return user;
        }
        throw new ValidationException("Пользователь не прошел валидацию.");
    }

    public User update(User user) {
        if (users.containsKey(user.getId())) {
            if (user.getEmail().isBlank()) {
                throw new InvalidEmailException("Адрес электронной почты не может быть пустым.");
            }
            if (users.get(user.getId()) == null) {
                throw new ValidationException("Id не совпадают.");
            }
            users.put(user.getId(), user);

            return user;
        }
        throw new UserNotFoundException(user.getId());
    }

    @Override
    public User find(Long id) {
        if (users.containsKey(id)) {
            return users.get(id);
        }
        throw new UserNotFoundException(id);
    }

    @Override
    public boolean delete(Long id) {
        if (users.containsKey(id)) {
            users.remove(id);
            return true;
        }
        return false;
    }

    public boolean addFriend(Long userId, Long friendId) {
        if (!users.containsKey(userId) || !users.containsKey(friendId) ||
                users.get(userId).getFriends().contains(friendId)) {
            throw new UserNotFoundException(userId);
        }
        find(userId).addFriend(friendId);
        find(friendId).addFriend(userId);
        return true;

    }

    public boolean deleteFriend(Long userId, Long friendId) {
        return users.get(userId).deleteFriend(friendId);
    }

    public List<User> getAllFriends(Long id) {
        if (users.containsKey(id)) {
            List<User> users = new ArrayList<>();
            for (Long userId : find(id).getFriends()) {
                users.add(find(userId));
            }
            return users;
        }
        throw new UserNotFoundException(id);
    }

    public List<User> commonFriendsList(Long id, Long otherId) {
        if (users.containsKey(id) && users.containsKey(otherId)) {
            return getAllFriends(id).stream()
                    .filter(user -> getAllFriends(otherId).contains(user))
                    .collect(Collectors.toList());
        }
        return null;
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
