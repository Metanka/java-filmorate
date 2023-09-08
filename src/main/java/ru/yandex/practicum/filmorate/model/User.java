package ru.yandex.practicum.filmorate.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import ru.yandex.practicum.filmorate.exception.UserNotFoundException;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.PastOrPresent;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

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
    @JsonIgnore
    private Set<Long> friends = new HashSet<>();

    public User(String email, String login, LocalDate birthday) {
        this.email = email;
        this.login = login;
        this.birthday = birthday;
    }

    public boolean addFriend(Long id) {
        return friends.add(id);
    }

    public boolean deleteFriend(Long id) {
        if (friends.contains(id)) {
            return friends.remove(id);
        }
        throw  new UserNotFoundException(id);
    }
}
