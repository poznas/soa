package com.agh.soa.lab4.theatre;

import com.agh.soa.lab4.theatre.model.Seat;

public interface ISeatInfoService {

    Iterable<Seat> getSeatList();

}
