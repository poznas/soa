package com.agh.soa.lab3.book;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum Currency {
    ANY(0),
    PLN(1),
    EUR(4),
    USD(3);

    private final double plnExchangeRate;

    public double plnValue(double value) {
        return value * plnExchangeRate;
    }
}
