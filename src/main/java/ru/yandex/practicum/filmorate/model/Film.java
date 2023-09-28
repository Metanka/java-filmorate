package ru.yandex.practicum.filmorate.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import ru.yandex.practicum.filmorate.exception.UserNotFoundException;

import javax.validation.constraints.*;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Film {
    private long id;
    @NotBlank
    private String name;
    @NotBlank
    private String description;
    @NotNull
    private LocalDate releaseDate;
    @PositiveOrZero
    @NonNull
    private int duration;
    @JsonIgnore
    private Set<Long> likes = new HashSet<>();
    private Set<Genre> genres;
    private Mpa mpa;

    public Film(String name, String description, LocalDate releaseDate, int duration) {
        this.name = name;
        this.description = description;
        this.releaseDate = releaseDate;
        this.duration = duration;
    }

    public boolean addLike(Long id) {
        return likes.add(id);
    }

    public boolean deleteLike(Long id) {
        if (likes.contains(id)) {
            return likes.remove(id);
        }
        throw new UserNotFoundException(id);
    }
}
