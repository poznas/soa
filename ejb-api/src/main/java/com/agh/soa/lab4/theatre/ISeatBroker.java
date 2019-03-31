package com.agh.soa.lab4.theatre;

import com.agh.soa.lab4.theatre.model.SeatBookException;

public interface ISeatBroker {

    void bookSeat(int id) throws SeatBookException;
}
