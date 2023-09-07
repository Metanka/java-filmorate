package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exception.UserNotFoundException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.service.UserService;
import ru.yandex.practicum.filmorate.storage.user.InMemoryUserStorage;

import javax.validation.Valid;
import java.util.List;

@Slf4j
@RestController
@Validated
@RequestMapping("/users")
public class UserController {
    private final InMemoryUserStorage userStorage;
    private final UserService userService;

    @Autowired
    public UserController(InMemoryUserStorage userStorage) {
        this.userStorage = userStorage;
        this.userService = new UserService(userStorage);
    }

    @GetMapping
    public List<User> findAll() {
        log.debug("Пришел GET запрос /users");
        List<User> response = userStorage.findAll();
        log.debug("Отправлен ответ GET /users с телом: {}", response);
        return response;
    }

    @PostMapping
    public User createUser(@RequestBody @Valid User user) {
        log.debug("Получен запрос POST /users: " + user);
        User response = userStorage.create(user);
        log.debug("Отправлен ответ: " + response);
        return response;
    }

    @Validated
    @PutMapping
    public User updateUser(@RequestBody @Valid User user) {
        log.debug("Получен запрос PUT /users: " + user);
        User response = userStorage.update(user);
        if (response != null) {
            log.debug("Отправлен ответ: " + response);
            return response;
        }
        throw new UserNotFoundException(user.getId());
    }

    @GetMapping("{id}")
    public User findById(@PathVariable Long id) {
        User response = userService.find(id);
        log.debug("Отправлен ответ: " + response);
        return response;
    }

    @PutMapping("{id}/friends/{friendId}")
    public String addFriend(@PathVariable(value = "id") Long id,
                            @PathVariable(value = "friendId") Long friendId) {
        log.debug("ids " + id + "   "+ friendId);
        boolean isSuccess = userService.addFriend(id, friendId);
        String response = isSuccess ? "Друг успешно добавлен!" : "Друг уже есть.";
        log.debug("Отправлен ответ: " + response);
        return response;
    }

    @DeleteMapping("{id}/friends/{friendId}")
    public String deleteFriend(@PathVariable(value = "id") Long id,
                               @PathVariable(value = "friendId") Long friendId) {
        boolean isSuccess = userService.deleteFriend(id, friendId);
        String response = isSuccess ? "Друг успешно удален!" : "Друг уже удален.";
        log.debug("Отправлен ответ: " + response);
        return response;
    }

    @GetMapping("{id}/friends")
    public List<User> getFriendList(@PathVariable(value = "id") Long id) {
        List<User> response = userService.getAllFriends(id);
        log.debug("Отправлен ответ: " + response);
        return response;
    }

    @GetMapping("{id}/friends/common/{otherId}")
    public List<User> commonFriendsList(@PathVariable(value = "id") Long id,
                                        @PathVariable(value = "otherId") Long otherId) {
        List<User> response = userService.commonFriendsList(id, otherId);
        log.debug("Отправлен ответ: " + response);
        return response;
    }
}
