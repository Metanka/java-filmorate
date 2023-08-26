package ru.yandex.practicum.filmorate.model;

import lombok.Data;
import lombok.NonNull;

import javax.validation.constraints.PastOrPresent;
import java.time.LocalDate;

@Data
public class User {
    int id;

    @NonNull
    String email;
    @NonNull
    String login;
    @NonNull
    @PastOrPresent
    LocalDate birthday;
    String name;
}
