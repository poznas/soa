package com.agh.soa.lab3.cloth;

import lombok.Getter;

import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;

@Getter
@ApplicationScoped
@ManagedBean(name = "billboard")
public class Billboard {

    private static final String[] ads = {
            "https://www.wykop.pl/cdn/c3201142/comment_5ajp1nxRbdoCMoxyBxFYpbsnt62IsUu4.jpg",
            "https://www.wykop.pl/cdn/c3201142/comment_AIm8LhH51iMnSgjlXSqhB4gm1TUfGRJ9.jpg"
    };

    private int counter;

    public String getAd() {
        return Math.random() > 0.5 ? ads[0] : ads[1];
    }

    public void click() {
        counter++;
    }
}
