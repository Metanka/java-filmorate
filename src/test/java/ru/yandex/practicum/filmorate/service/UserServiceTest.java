package ru.yandex.practicum.filmorate.service;

import org.junit.Test;
import org.junit.jupiter.api.BeforeAll;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.User;

import java.time.LocalDate;
import java.time.Month;

import static org.junit.Assert.*;
import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class UserServiceTest {
    private static final UserService userService = new UserService();
    private static User user1;
    private static User user2;

    @BeforeAll
    public static void beforeAll() {
        user1 = new User("me@me.ru", "Login1", LocalDate.of(1993, Month.SEPTEMBER, 9));
        user2 = new User("ya@ya.ru", "dalare", LocalDate.of(1946, Month.AUGUST, 20));
    }

    @Test
    public void testUserService() {
        user1 = new User("me@me.ru", "Login1", LocalDate.of(1993, Month.SEPTEMBER, 9));
        user2 = new User("ya@ya.ru", "dalare", LocalDate.of(1946, Month.AUGUST, 20));
        userService.createUser(user1);
        userService.createUser(user2);
        assertEquals(2, userService.findAllUser().size());

        User user3 = new User("ya@ya.ru", "dolore", LocalDate.of(1946, Month.AUGUST, 20));
        userService.updateUser(user3);
        assertTrue(userService.findAllUser().contains(user3));
    }

    @Test
    public void validationTest() {
        User user3 = new User("3", "re",
                LocalDate.of(2003, Month.APRIL, 27));
        Throwable exception1 = assertThrows(ValidationException.class, () -> userService.createUser(user3));
        assertEquals("Проверьте правильность email", exception1.getMessage());

        User user4 = new User("pe@pe.ru", "",
                LocalDate.of(2003, Month.APRIL, 27));
        Throwable exception2 = assertThrows(ValidationException.class, () -> userService.createUser(user4));
        assertEquals("Логин не может быть пустым", exception2.getMessage());

        User user5 = new User("pe@pe.ru", "fwf",
                LocalDate.of(2053, Month.APRIL, 27));
        Throwable exception3 = assertThrows(ValidationException.class, () -> userService.createUser(user5));
        assertEquals("Пользователь должен быть рожденым", exception3.getMessage());
    }
}
