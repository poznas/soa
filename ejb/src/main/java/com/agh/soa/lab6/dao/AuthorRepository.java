package com.agh.soa.lab6.dao;

import com.agh.soa.lab5.AbstractRepository;
import com.agh.soa.lab6.model.Author;

import javax.ejb.Stateful;
import javax.ejb.TransactionManagement;

import static javax.ejb.TransactionManagementType.BEAN;

@Stateful
@TransactionManagement(BEAN)
public class AuthorRepository extends AbstractRepository<Author> {

    @Override
    protected Class<Author> getType() {
        return Author.class;
    }
}
