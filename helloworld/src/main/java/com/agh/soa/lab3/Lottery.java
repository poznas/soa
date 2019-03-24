package com.agh.soa.lab3;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;


@RequestScoped
@ManagedBean(name = "Lottery")
public class Lottery {

    public String submit() {
        return Math.random() > 0.2 ? "win" : "loose";
    }
}
