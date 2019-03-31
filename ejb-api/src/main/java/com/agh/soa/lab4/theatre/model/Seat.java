package com.agh.soa.lab4.theatre.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Setter
@Getter
@RequiredArgsConstructor(staticName = "of")
public class Seat implements Serializable {

    private final Integer id;
    private final String seatClass;
    private final Integer price;
    private boolean available = true;
}
