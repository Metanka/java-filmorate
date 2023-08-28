package ru.yandex.practicum.filmorate.model;

import lombok.Data;
import lombok.NonNull;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;
import java.time.LocalDate;

@Data
public class Film {
    int id;
    @NonNull
    @NotBlank
    String name;
    @NonNull
    @NotBlank
    String description;
    @NonNull
    LocalDate releaseDate;
    @Positive
    @NonNull
    int duration;
}
