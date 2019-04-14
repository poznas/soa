package com.agh.soa.lab6.dao;

import com.agh.soa.lab5.AbstractRepository;
import com.agh.soa.lab6.model.LibraryBook;

import javax.ejb.Stateful;
import javax.ejb.TransactionManagement;

import static javax.ejb.TransactionManagementType.BEAN;

@Stateful
@TransactionManagement(BEAN)
public class LibraryBookRepository extends AbstractRepository<LibraryBook> {

    @Override
    protected Class<LibraryBook> getType() {
        return LibraryBook.class;
    }
}
