package com.agh.soa.lab4.theatre;

import com.agh.soa.lab4.theatre.model.Seat;

import javax.ejb.EJB;
import javax.ejb.Remote;
import javax.ejb.Stateless;

import static java.util.stream.Collectors.toList;

@Stateless
@Remote(ISeatInfoService.class)
public class SeatInfoService implements ISeatInfoService {

    @EJB
    SeatRepository seatRepository;

    @Override
    public Iterable<Seat> getSeatList() {
        return seatRepository.getSeatList().stream().filter(Seat::isAvailable).collect(toList());
    }
}
