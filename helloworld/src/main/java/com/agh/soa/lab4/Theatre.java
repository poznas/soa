package com.agh.soa.lab4;

import com.agh.soa.lab4.theatre.ISeatBroker;
import com.agh.soa.lab4.theatre.ISeatInfoService;
import com.agh.soa.lab4.theatre.model.SeatBookException;
import lombok.Getter;
import lombok.Setter;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

@Getter
@SessionScoped
@ManagedBean(name = "theatre")
public class Theatre {

    @EJB(lookup = "java:global/ejb/SeatInfoService")
    private ISeatInfoService seatInfoService;

    @EJB(lookup = "java:global/ejb/SeatBroker")
    private ISeatBroker seatBroker;

    @Setter
    private Integer seatId;

    private String errorMessage;

    public void buySeat() {
        try {
            seatBroker.bookSeat(seatId);
            errorMessage = null;
        } catch (SeatBookException e) {
            errorMessage = e.getMessage();
        }
    }
}
