package com.agh.soa.lab4.theatre;

import com.agh.soa.lab4.theatre.model.SeatBookException;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.Remote;
import javax.ejb.Stateful;

@Stateful
@Remote(ISeatBroker.class)
public class SeatBroker implements ISeatBroker {

    private Integer availableMoney;

    @EJB
    SeatRepository seatRepository;

    @PostConstruct
    public void init() {
        availableMoney = 100;
    }

    @Override
    public void bookSeat(int id) throws SeatBookException {
        var seat = seatRepository.getSeat(id);

        if (!seat.isAvailable()) {
            throw SeatBookException.SEAT_BOOKED;
        } else if (seat.getPrice() > availableMoney) {
            throw SeatBookException.NOT_ENOUGH_MONEY;
        }

        seatRepository.buyTicket(id);
        availableMoney = availableMoney - seat.getPrice();
    }
}
