package com.agh.soa.lab4.theatre.model;

public class SeatBookException extends Exception {

    public static final SeatBookException NOT_ENOUGH_MONEY = new SeatBookException("Error: not enough money");
    public static final SeatBookException SEAT_BOOKED = new SeatBookException("Error: seat is already booked");

    public SeatBookException(String message) {
        super(message);
    }
}
