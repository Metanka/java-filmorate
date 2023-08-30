package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.service.UserService;

import javax.validation.Valid;
import java.util.List;

@Slf4j
@RestController
@Validated
@RequestMapping("/users")
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public List<User> findAll() {
        log.debug("Пришел GET запрос /users");
        List<User> response = userService.findAll();
        log.debug("Отправлен ответ GET /users с телом: {}", response);
        return response;
    }

    @PostMapping
    public User createUser(@RequestBody @Valid User user) {
        log.debug("Получен запрос POST /users: " + user);
        User response = userService.create(user);
        log.debug("Отправлен ответ: " + response);
        return response;
    }

    @Validated
    @PutMapping
    public User updateUser(@RequestBody @Valid User user) {
        log.debug("Получен запрос PUT /users: " + user);
        User response = userService.update(user);
        log.debug("Отправлен ответ: " + response);
        return response;
    }
}
