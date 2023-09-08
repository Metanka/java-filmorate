package ru.yandex.practicum.filmorate.service;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.storage.film.InMemoryFilmStorage;
import ru.yandex.practicum.filmorate.storage.user.InMemoryUserStorage;

import java.time.LocalDate;
import java.time.Month;

import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class FilmServiceTest {
    private static final InMemoryFilmStorage filmStorage = new InMemoryFilmStorage();
    private static final InMemoryUserStorage userStorage = new InMemoryUserStorage();
    private static final FilmService filmService = new FilmService(filmStorage, userStorage);

    private static Film film1;
    private static Film film2;

    @BeforeAll
    public static void beforeAll() {
        film1 = new Film("1", "Описание1",
                LocalDate.of(2011, Month.APRIL, 28), 30);
        film2 = new Film("2", "Описание2",
                LocalDate.of(2011, Month.APRIL, 27), 60);
    }

    @Test
    public void testFilmService() {
        filmService.create(film1);
        filmService.create(film2);
        assertEquals(2, filmService.findAll().size());

        film2.setName("hhhhh");

        filmService.update(film2);
        assertTrue(filmService.findAll().contains(film2));
    }

    @Test
    public void validationTest() {
        Film film3 = new Film("3", "Описание2",
                LocalDate.of(1333, Month.APRIL, 27), 90);
        Throwable exception1 = assertThrows(ValidationException.class, () -> filmService.create(film3));
        assertEquals("В те года фильмов еще не было", exception1.getMessage());

        Film film4 = new Film("4", "Из числа всей ее челяди самым замечательным лицом был дворник Герасим, мужчина двенадцати вершков роста, сложенный богатырем и глухонемой от рожденья. Барыня взяла его из деревни, где он жил один, в небольшой избушке, отдельно от братьев, и считался едва ли не самым исправным тягловым мужиком. Одаренный необычайной силой, он работал за четверых — дело спорилось в его руках, и весело было смотреть на него, когда он либо пахал и, налегая огромными ладонями на соху, казалось, один, без помощи лошаденки, взрезывал упругую грудь земли, либо о Петров день так сокрушительно действовал косой, что хоть бы молодой березовый лесок смахивать с корней долой, либо проворно и безостановочно молотил трехаршинным цепом, и как рычаг опускались и поднимались продолговатые и твердые мышцы его плечей. Постоянное безмолвие придавало торжественную важность его неистомной работе. Славный он был мужик, и не будь его несчастье, всякая девка охотно пошла бы за него замуж… Но вот Герасима привезли в Москву, купили ему сапоги, сшили кафтан на лето, на зиму тулуп, дали ему в руки метлу и лопату и определили его дворником.",
                LocalDate.of(2011, Month.APRIL, 27), 90);
        Throwable exception2 = assertThrows(ValidationException.class, () -> filmService.create(film4));
        assertEquals("Описание фильма превышает 200 символов", exception2.getMessage());
    }
}