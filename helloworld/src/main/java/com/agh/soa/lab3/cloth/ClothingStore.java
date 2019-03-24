package com.agh.soa.lab3.cloth;

import lombok.Getter;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

@Getter
@SessionScoped
@ManagedBean(name = "cloth")
public class ClothingStore {

    private Client client = new Client();
    private boolean firstFormAccepted;

    public Gender[] getGenders() {
        return Gender.values();
    }

    public void acceptFirstForm() {
        firstFormAccepted = true;
    }

}
