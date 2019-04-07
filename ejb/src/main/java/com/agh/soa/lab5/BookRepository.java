package com.agh.soa.lab5;

import com.agh.soa.IBookRepository;
import com.agh.soa.lab5.model.Book;

import javax.ejb.Remote;
import javax.ejb.Stateful;
import javax.ejb.TransactionManagement;

import static javax.ejb.TransactionManagementType.BEAN;

@Stateful
@TransactionManagement(BEAN)
@Remote(IBookRepository.class)
public class BookRepository extends AbstractRepository<Book> implements IBookRepository {

    public BookRepository() {
        setType(Book.class);
    }
}
