package ru.yandex.practicum.filmorate.model;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.PastOrPresent;
import java.time.LocalDate;

@Data
public class User {
    private long id;

    @Email(message = "Email должен быть корректным адресом электронной почты")
    @NotBlank
    private String email;
    @NotBlank
    private String login;

    @PastOrPresent
    private LocalDate birthday;
    private String name;

    public User(String email, String login, LocalDate birthday) {
        this.email = email;
        this.login = login;
        this.birthday = birthday;
    }
}
