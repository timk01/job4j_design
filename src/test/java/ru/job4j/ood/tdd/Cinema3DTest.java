package ru.job4j.ood.tdd;

import org.junit.jupiter.api.Test;

import java.util.Calendar;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class Cinema3DTest {
    @Test
    public void whenBuyThenGetTicket() {
        Account account = new AccountCinema();
        Cinema cinema = new Cinema3D();
        Calendar date = Calendar.getInstance();
        Ticket ticket = cinema.buy(account, 1, 1, date);
        assertThat(ticket).isEqualTo(new Ticket3D());
    }

    @Test
    public void whenBuyWithNotValidRowAndColumnThenThrowException() {
        Account account = new AccountCinema();
        Cinema cinema = new Cinema3D();
        Calendar date = Calendar.getInstance();
        assertThatThrownBy(() -> cinema.buy(account, -1, -1, date))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    public void whenBuyWithPastDateThenThrowException() {
        Account account = new AccountCinema();
        Cinema cinema = new Cinema3D();
        Calendar wrongDate = Calendar.getInstance();
        wrongDate.add(Calendar.DAY_OF_MONTH, -1);
        assertThatThrownBy(() -> cinema.buy(account, 1, 1, wrongDate))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    public void whenBuyWithNullDateThenThrowException() {
        Account account = new AccountCinema();
        Cinema cinema = new Cinema3D();
        assertThatThrownBy(() -> cinema.buy(account, 1, 1, null))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    public void whenBuyWithNullAccountThenThrowException() {
        Cinema cinema = new Cinema3D();
        Calendar date = Calendar.getInstance();
        assertThatThrownBy(() -> cinema.buy(null, 1, 1, date))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    public void whenAddSessionThenItExistsBetweenAllSessions() {
        Cinema cinema = new Cinema3D();
        Session session = new Session3D();
        cinema.add(session);
        List<Session> sessions = cinema.find(data -> true);
        assertThat(sessions).contains(session);
    }

    @Test
    public void whenSessionDoesNotExistThenItCanNotBeFound() {
        Cinema cinema = new Cinema3D();
        assertThat(cinema.find(data -> true)).isEmpty();
    }

    @Test
    public void whenAddedSessionIsNullThenException() {
        Cinema cinema = new Cinema3D();
        assertThatThrownBy(() -> cinema.add(null))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    public void whenFilterIsNullInSessionsFindingThenException() {
        Cinema cinema = new Cinema3D();
        assertThatThrownBy(() -> cinema.find(null))
                .isInstanceOf(IllegalArgumentException.class);
    }
}