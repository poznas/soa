package com.agh.soa.lab6.dao;

import com.agh.soa.lab5.AbstractRepository;
import com.agh.soa.lab6.model.Reader;

import javax.ejb.Stateful;
import javax.ejb.TransactionManagement;

import static javax.ejb.TransactionManagementType.BEAN;

@Stateful
@TransactionManagement(BEAN)
public class ReaderRepository extends AbstractRepository<Reader> {

    @Override
    protected Class<Reader> getType() {
        return Reader.class;
    }
}
