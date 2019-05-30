package com.agh.soa.lab4.theatre;

import static com.agh.soa.utils.CollectionUtils.filterList;

import com.agh.soa.lab4.theatre.model.Seat;
import javax.ejb.EJB;
import javax.ejb.Remote;
import javax.ejb.Stateless;

@Stateless
@Remote(ISeatInfoService.class)
public class SeatInfoService implements ISeatInfoService {

    @EJB
    SeatRepository seatRepository;

    @Override
    public Iterable<Seat> getSeatList() {
        return filterList(seatRepository.getSeatList(), Seat::isAvailable);
    }
}
