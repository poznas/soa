package com.agh.soa.lab4;

import com.agh.soa.lab4.add.ITestAddBean;

import javax.ejb.Remote;
import javax.ejb.Stateless;

@Stateless
@Remote(ITestAddBean.class)
public class TestAddBean implements ITestAddBean {

    public int add(int a,int b){
        return a+b;
    }
}
