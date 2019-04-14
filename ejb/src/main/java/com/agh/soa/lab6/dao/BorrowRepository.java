package com.agh.soa.lab6.dao;

import com.agh.soa.lab5.AbstractRepository;
import com.agh.soa.lab6.model.Borrow;

import javax.ejb.Stateful;
import javax.ejb.TransactionManagement;

import static javax.ejb.TransactionManagementType.BEAN;

@Stateful
@TransactionManagement(BEAN)
public class BorrowRepository extends AbstractRepository<Borrow> {

    @Override
    protected Class<Borrow> getType() {
        return Borrow.class;
    }
}
