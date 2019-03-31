package com.agh.soa.lab4;

import com.agh.soa.lab4.counter.ITestBeanCounter;
import lombok.Getter;

import javax.ejb.Remote;
import javax.ejb.Singleton;

@Getter
@Singleton
@Remote(ITestBeanCounter.class)
public class TestBeanCounter implements ITestBeanCounter {

    private long number = 0;

    @Override
    public void increment() {
        number++;
    }
}
