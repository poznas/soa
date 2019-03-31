package com.agh.soa.lab4.theatre;

import com.agh.soa.lab4.theatre.model.Seat;

import javax.annotation.PostConstruct;
import javax.ejb.Lock;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.IntStream;

import static javax.ejb.LockType.READ;
import static javax.ejb.LockType.WRITE;

@Startup
@Singleton
public class SeatRepository {

    private List<Seat> seats = new ArrayList<>();

    @PostConstruct
    public void initSeats() {
        Map.of("Normal", 10, "Silver", 20, "Gold", 30).forEach((seatLevel, price) ->
                IntStream.range(0, 5)
                        .mapToObj(i -> Seat.of((i + (price - 10) / 2), seatLevel, price))
                        .forEach(seats::add));
    }

    @Lock(READ)
    public List<Seat> getSeatList() {
        return seats;
    }

    public Seat getSeat(int id) {
        return getSeatList().stream().filter(s -> s.getId() == id).findFirst().orElseThrow();
    }

    @Lock(READ)
    public int getSeatPrice(int id) {
        return getSeatList().get(id).getPrice();
    }

    @Lock(WRITE)
    public void buyTicket(int seatId) {
        getSeat(seatId).setAvailable(false);
    }
}
